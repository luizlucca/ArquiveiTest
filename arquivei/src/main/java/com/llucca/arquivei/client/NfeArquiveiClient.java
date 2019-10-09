package com.llucca.arquivei.client;

import com.llucca.arquivei.adapter.NfeAdapter;
import com.llucca.arquivei.exception.ArquiveApiException;
import com.llucca.arquivei.exception.MessageSendException;
import com.llucca.arquivei.model.client.NfeEncoded;
import com.llucca.arquivei.model.client.NfeResponse;
import com.llucca.arquivei.service.message.MessageServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Consulta a api da arquivei e posta as notas na fila para que o worker processe.
 * As mensagens da fila devem conter a nota no formato xml (nao em base64)
 */

@Slf4j
@Service("NfeArquiveiClient")
public class NfeArquiveiClient implements NfeClient {

    private static final String arquiveNfeUrl = "https://sandbox-api.arquivei.com.br/v1/nfe/received";

    @Value("${arquivei.xApiId.header}")
    private String arqXApiHeader;

    @Value("${arquivei.xApiId.value}")
    private String arqXApiValue;

    @Value("${arquivei.xApiKey.header}")
    private String arqXApiKeyHeader;

    @Value("${arquivei.xApiKey.value}")
    private String arqXApiKeyValue;

    @Value("${redis.nfe.key}")
    private String redisNfeKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("RabbitMessageServiceInterface")
    private MessageServiceInterface rabbitMessageService;

    @Autowired
    @Qualifier("NfeAdapter")
    private NfeAdapter nfeAdapter;

    @Autowired
    private RedisTemplate redisTemplate;

    public void consumeNfe() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(arqXApiHeader, arqXApiValue);
        headers.add(arqXApiKeyHeader, arqXApiKeyValue);

        HttpEntity entity = new HttpEntity(headers);
        boolean hasMoreNfes = true;
        String nextRegistersUrl = arquiveNfeUrl;

        //processa ate que nao tenha mais paginas para consumir
        while (hasMoreNfes) {

            ResponseEntity<NfeResponse> responseEntity = null;
            try {
                responseEntity = restTemplate.exchange(nextRegistersUrl, HttpMethod.GET, entity, NfeResponse.class);

                if (responseEntity == null) {
                    log.warn("Null Response Entity.");
                    return;
                }

            } catch (Exception e) {
                log.error("error at Arquivei Api", e);
                throw new ArquiveApiException("eEror at Arquivei Api");
            }
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {

                NfeResponse nfeResponse = responseEntity.getBody();

                try {
                    //todas as mensagens sao enviadas para o rabbit. o Worker eh quem processa joga pro banco e cache
                    for (NfeEncoded nfeEncoded : nfeResponse.getData()) {
                        String nfeXml = new String(Base64.decodeBase64(nfeEncoded.getXml().getBytes()));
                        rabbitMessageService.sendMessage(nfeAdapter.convertFromNfeEncodedToMessage(nfeEncoded));
                    }
                } catch (Exception e) {
                    //aqui seria uma boa ideia utilizar o Hystrix (fallback) e salvar a NFE em um arquivo, ao inves do rabbit
                    //a ideia seria consumir do arquivo (se houver) e depois enviar para o rabbit.
                    log.error("Error sending message to Rabbit", e);
                    throw new MessageSendException("Error sending message to Rabbit");

                }

                if (nfeResponse.getCount() < 50) {
                    hasMoreNfes = false;
                } else {
                    nextRegistersUrl = nfeResponse.getPage().getNext();
                }
            }
        }
    }
}
