package com.llucca.arquivei.service;

import com.llucca.arquivei.model.Nfe;
import com.llucca.arquivei.repository.NfeRepository;
import com.llucca.arquivei.service.cache.RedisCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NfeServiceTest {

    @Autowired
    private NfeService nfeService;


    @MockBean
    private RedisCacheService redisCacheService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private NfeRepository nfeRepository;

    @Test
    public void testFindByNfeWithCache(){
        Mockito.when(redisCacheService.getFromChache(Mockito.any())).thenReturn(200.0);
        nfeService.findByNfe("10");
        Mockito.verify(redisCacheService, times(1)).getFromChache(Mockito.any());
        Mockito.verify(nfeRepository, times(0)).findByAccessKey(Mockito.any());
    }

    @Test
    public void testFindByNfeWithCacheAndDB(){
        Mockito.when(redisCacheService.getFromChache(Mockito.any())).thenReturn(null);
        Mockito.when(nfeRepository.findByAccessKey(Mockito.any())).thenReturn(Optional.of(new Nfe()));

        nfeService.findByNfe("10");

        Mockito.verify(redisCacheService, times(1)).getFromChache(Mockito.any());
        Mockito.verify(nfeRepository, times(1)).findByAccessKey(Mockito.any());
    }
}
