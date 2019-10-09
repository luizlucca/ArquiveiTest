package com.llucca.arquivei.service.message;

import com.llucca.arquivei.model.message.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servico publisher de mensagens. Usamos o rabbit para fila.
 */

@Service("RabbitMessageServiceInterface")
public class RabbitMessageServiceInterface implements MessageServiceInterface {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public void sendMessage(Message message) {
        template.convertAndSend(queue.getName(), message);
    }

}
