package com.llucca.arquivei.service;

import com.llucca.arquivei.client.NfeClient;
import com.llucca.arquivei.exception.ArquiveApiException;
import com.llucca.arquivei.exception.DatabaseAccessException;
import com.llucca.arquivei.model.Nfe;
import com.llucca.arquivei.repository.NfeRepository;
import com.llucca.arquivei.service.cache.CacheServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servico para consulta de nota e processamento de dados.
 */

@Slf4j
@Service
public class NfeService {

    @Autowired
    private NfeRepository nfeRepository;

    @Autowired
    @Qualifier("RedisCacheService")
    private CacheServiceInterface redisCache;

    @Autowired
    @Qualifier("NfeArquiveiClient")
    private NfeClient nfeArquiveiClient;

    public Double findByNfe(String accessKey) {
        //tenta primeio recuperar do cache. se nao conseguir, vai ate o banco de dados
        Double value = null;
        try {
            value = (Double) redisCache.getFromChache(accessKey);
        } catch (Exception e) {
            log.warn("Fail to contact or recover data from cache. I will trie DB.", e);
        }

        try {
            if (value == null) {
                Optional<Nfe> bdNfe = nfeRepository.findByAccessKey(accessKey);
                if (bdNfe.isPresent()) {
                    value = bdNfe.get().getTotalValue();
                    redisCache.putToChache(bdNfe.get().getAccessKey(), bdNfe.get().getTotalValue());
                }
            }
            return value;

        } catch (Exception e) {
            log.error("Error on recover data from DB. Aborting...", e);
            throw new DatabaseAccessException("Error on recover data from DB. Aborting...");
        }

    }

    public void fetchFromArquiveiApi() {
        try {
            nfeArquiveiClient.consumeNfe();
        } catch (Exception e) {
            log.error("Error on get data from Arquivei api. Aborting...", e);
            throw new ArquiveApiException("Error on get data from Arquivei api. Aborting...");
        }
    }
}
