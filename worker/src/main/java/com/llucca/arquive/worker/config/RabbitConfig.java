package com.llucca.arquive.worker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuracao do rabbit com uma fila e sua respectiva dlq
 */

@Configuration
public class RabbitConfig {

    @Value("${rabbit.nfe.queue}")
    private String nfeQueue;

    @Value("${rabbit.nfe.dlq.queue}")
    private String nfeDlqQueue;

    @Bean
    Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-queue-type", "classic");

        return new Queue(nfeDlqQueue, true, false, false, arguments);
    }

    @Bean
    Queue queue() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", nfeDlqQueue);
        args.put("x-queue-type", "classic");
        return new Queue(nfeQueue, true, false, false, args);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

}
