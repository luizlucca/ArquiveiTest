package com.llucca.arquive.worker.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llucca.arquive.worker.consumers.Consumer;
import com.llucca.arquive.worker.model.message.Message;
import com.llucca.arquive.worker.model.message.nfe.NfeMessage;
import com.llucca.arquive.worker.model.nfe.Nfe;
import com.llucca.arquive.worker.repository.NfeRepository;
import com.llucca.arquive.worker.service.cache.CacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitConsumerTest {

    @Autowired
    @Qualifier("RabbitConsumer")
    private Consumer rabbitConsumer;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private NfeRepository nfeRepository;

    @MockBean
    @Qualifier("RedisCacheService")
    private CacheService redisCacheService;

    @Test
    public void testConsumeMessageAlreadyCached() throws IOException {

        byte[] b = {1,2,3,4,5};

        NfeMessage nfeMessage = new NfeMessage();
        nfeMessage.setAccessKey("123");
        nfeMessage.setXml("aaaaaaaa<vNF>12.12</vNF>aaaaaaa");

        Message<NfeMessage> message = new Message<>();
        message.setBody(nfeMessage);

        Nfe nfe = new Nfe();
        nfe.setAccessKey("123");

        Mockito.when( objectMapper.readValue(Mockito.anyString(), Matchers.<TypeReference<Message<NfeMessage>>>any())).thenReturn(message);
        Mockito.when(nfeRepository.save(Mockito.any())).thenReturn(nfe);
        Mockito.when(redisCacheService.getFromChache(Mockito.any())).thenReturn("123");
        Mockito.doNothing().when(redisCacheService).putToChache(Mockito.any(),Mockito.any());

        rabbitConsumer.receiveMessage(b);

        Mockito.verify(redisCacheService, times(1)).getFromChache(Mockito.any());
        Mockito.verify(redisCacheService, times(0)).putToChache(Mockito.any(), Mockito.any());
        Mockito.verify(nfeRepository, times(0)).save(Mockito.any());
    }

    @Test
    public void testConsumeMessageNotCached() throws IOException {

        byte[] b = {1,2,3,4,5};

        NfeMessage nfeMessage = new NfeMessage();
        nfeMessage.setAccessKey("123");
        nfeMessage.setXml("aaaaaaaa<vNF>12.12</vNF>aaaaaaa");

        Message<NfeMessage> message = new Message<>();
        message.setBody(nfeMessage);

        Nfe nfe = new Nfe();
        nfe.setAccessKey("123");

        Mockito.when( objectMapper.readValue(Mockito.anyString(), Matchers.<TypeReference<Message<NfeMessage>>>any())).thenReturn(message);
        Mockito.when(nfeRepository.save(Mockito.any())).thenReturn(nfe);
        Mockito.when(redisCacheService.getFromChache(Mockito.any())).thenReturn(null);
        Mockito.doNothing().when(redisCacheService).putToChache(Mockito.any(),Mockito.any());

        rabbitConsumer.receiveMessage(b);

        Mockito.verify(redisCacheService, times(1)).getFromChache(Mockito.any());
        Mockito.verify(redisCacheService, times(1)).putToChache(Mockito.any(), Mockito.any());
        Mockito.verify(nfeRepository, times(1)).save(Mockito.any());
    }
}
