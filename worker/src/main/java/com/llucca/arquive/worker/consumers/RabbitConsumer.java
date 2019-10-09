package com.llucca.arquive.worker.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llucca.arquive.worker.adapter.NfeAdapterInterface;
import com.llucca.arquive.worker.exception.DatabaseAccessException;
import com.llucca.arquive.worker.exception.MessageConsumeException;
import com.llucca.arquive.worker.model.message.Message;
import com.llucca.arquive.worker.model.message.nfe.NfeMessage;
import com.llucca.arquive.worker.model.nfe.Nfe;
import com.llucca.arquive.worker.repository.NfeRepository;
import com.llucca.arquive.worker.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumidor da fila.
 * As mensagens sao postadas pelo servico Arquivei.
 * As mensagensa sao processadas e inseridas no banco de dados e no cache
 */

@Slf4j
@Component("RabbitConsumer")
public class RabbitConsumer implements Consumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NfeRepository nfeRepository;

    @Autowired
    @Qualifier("NfeAdpater")
    private NfeAdapterInterface nfeAdapter;

    @Autowired
    @Qualifier("RedisCacheService")
    private CacheService redisCacheService;

    @Override
    @RabbitListener(queues = "${rabbit.nfe.queue}")
    public void receiveMessage(@Payload byte[] payload) {
        try {

            // if no message, reject the payload.
            if (payload == null) {
                throw new AmqpRejectAndDontRequeueException("Received message is null");
            }

            Message<NfeMessage> message;
            try {
                String messageBody = new String(payload);
                message = objectMapper.readValue(messageBody, new TypeReference<Message<NfeMessage>>() {
                });
            } catch (Exception e) {
                log.error("Error on convert message to expected format", e);
                throw new MessageConsumeException("Error on convert message to expected format");
            }

            Nfe nfe = nfeAdapter.convertFromNfeMessage(message.getBody());

            Nfe savedNfe;
            try {
                savedNfe = nfeRepository.save(nfe);
            } catch (Exception e) {
                log.error("Error on persist entity to DB", e);
                throw new DatabaseAccessException("Error on persist entity to DB");
            }

            if (redisCacheService.getFromChache(savedNfe.getAccessKey()) == null) {
                log.debug("persist DB for new record");
                redisCacheService.putToChache(savedNfe.getAccessKey(), savedNfe.getTotalValue());
            }

            log.debug("Message -- " + message.getBody().getAccessKey());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
