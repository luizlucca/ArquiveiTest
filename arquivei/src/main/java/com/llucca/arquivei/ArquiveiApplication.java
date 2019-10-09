package com.llucca.arquivei;

import com.llucca.arquivei.exception.ArquiveApiException;
import com.llucca.arquivei.service.NfeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Aplicacao responsavel por consumir a api da Arquivei e publicar mensagens na fila que sera consumida pelo worker
 * Aqui tbm estao os endpoints de consulta e processamento (forcado) dos dados da arquivei.
 * Toda vez que a aplicacao sobe, ela consulta as notas da api da Arquivei.
 * Endereco do swagger (se subir localmente):
 * - http://localhost:8080/swagger-ui.html
 */

@Slf4j
@SpringBootApplication
@EnableSwagger2
public class ArquiveiApplication implements CommandLineRunner {

    @Autowired
    private NfeService nfeService;

    public static void main(String[] args) {
        SpringApplication.run(ArquiveiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            nfeService.fetchFromArquiveiApi();
        } catch (Exception e) {
            log.error("Error on get data from Arquivei api. Aborting...", e);
            throw new ArquiveApiException("Error on get data from Arquivei api. Aborting...");
        }
    }
}
