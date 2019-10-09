package com.llucca.arquivei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuracoes do resttemplate para nao ficar esperando eternamente uma resposta da api da Arquivei
 */

@Configuration
public class RestTemplateConfig {

    @Value("${restemplate.connection.timeout}")
    private Integer conTimeout;

    @Value("${restemplate.connection.read.timeout}")
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(conTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
