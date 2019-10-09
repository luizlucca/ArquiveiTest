package com.llucca.arquive.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicao responsavel por consumir mensagem da fila e persistir a chave de acesso e valor total da notal em um banco de dados
 * Usamos cache para verificar se nao existe o valor no banco. Se nao existir, persisite no banco e salva no cache.
 * Cache aside -> exita que fiquemos batendo no banco de dados em todos os momentos e otimiza a consulta.
 */

@SpringBootApplication
public class WorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
}
