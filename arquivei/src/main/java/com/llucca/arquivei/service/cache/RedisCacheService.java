package com.llucca.arquivei.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Servico de cache. Usamos o redis, mas poderia ser qualquer outro.
 * O redis esta na frente da consulta ao banco. Se nao tiver o dado no redis, a consulta eh feita no banco de dados
 */

@Service("RedisCacheService")
public class RedisCacheService implements CacheServiceInterface {

    @Value("${redis.nfe.key}")
    private String redisNfeKey;

    @Autowired
    private RedisTemplate redisTemplate;

    public Object getFromChache(Object hash) {
        return redisTemplate.opsForHash().get(redisNfeKey, hash);
    }

}
