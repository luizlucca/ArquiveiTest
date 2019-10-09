package com.llucca.arquivei.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuracoes do rabbit para fila e dlq.
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
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
