package com.llucca.arquivei.client;

import com.llucca.arquivei.exception.ArquiveApiException;
import com.llucca.arquivei.model.client.NfeEncoded;
import com.llucca.arquivei.model.client.NfeResponse;
import com.llucca.arquivei.service.message.MessageServiceInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NfeArquiveiClientTest {

    @Autowired
    private NfeArquiveiClient nfeArquiveiClient;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    @Qualifier("RabbitMessageServiceInterface")
    private MessageServiceInterface rabbitMessageService;

    @Test
    public void testClientSuccess(){

        String xmlEncoded= "eG1sbGxsbA==";
        List<NfeEncoded> nfeEncodedList =  new ArrayList<>();
        nfeEncodedList.add(new NfeEncoded("123", xmlEncoded));
        nfeEncodedList.add(new NfeEncoded("1234", xmlEncoded));

        NfeResponse nfeResponse = new NfeResponse();
        nfeResponse.setCount(2);
        nfeResponse.setData(nfeEncodedList);

        ResponseEntity<NfeResponse> responseEntity =  ResponseEntity.status(HttpStatus.OK).body(nfeResponse);

        Mockito.when(restTemplate.exchange(Mockito.anyString(),  Mockito.eq(HttpMethod.GET), Mockito.any(),  Mockito.eq(NfeResponse.class))).thenReturn(responseEntity);
        Mockito.doNothing().when(rabbitMessageService).sendMessage(Mockito.any());
        nfeArquiveiClient.consumeNfe();

        Mockito.verify(rabbitMessageService, times(2)).sendMessage(Mockito.any());

    }

    @Test(expected = ArquiveApiException.class)
    public void testClientApiError(){

        String xmlEncoded= "eG1sbGxsbA==";
        List<NfeEncoded> nfeEncodedList =  new ArrayList<>();
        nfeEncodedList.add(new NfeEncoded("123", xmlEncoded));
        nfeEncodedList.add(new NfeEncoded("1234", xmlEncoded));

        NfeResponse nfeResponse = new NfeResponse();
        nfeResponse.setCount(2);
        nfeResponse.setData(nfeEncodedList);

        ResponseEntity<NfeResponse> responseEntity =  ResponseEntity.status(HttpStatus.OK).body(nfeResponse);

        Mockito.when(restTemplate.exchange(Mockito.anyString(),  Mockito.eq(HttpMethod.GET), Mockito.any(),  Mockito.eq(NfeResponse.class))).thenThrow(new RuntimeException());

        nfeArquiveiClient.consumeNfe();

    }


}
