package com.llucca.arquive.worker.service.cache;

import com.llucca.arquive.worker.exception.RedisAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Servico de cache.
 * Usamos o redis para implementacao, mas poderia ser qualquer outro.
 */

@Slf4j
@Service("RedisCacheService")
public class RedisCacheService implements CacheService {

    @Value("${redis.nfe.key}")
    private String redisNfeKey;

    @Autowired
    private RedisTemplate redisTemplate;

    public Object getFromChache(Object hash) {
        try {
            return redisTemplate.opsForHash().get(redisNfeKey, hash);
        } catch (Exception e) {
            log.error("Error acessing redis to acquire value", e);
            throw new RedisAccessException("Error acessing redis to acquire value");
        }
    }

    @Override
    public void putToChache(Object hash, Object value) {
        try {
            redisTemplate.opsForHash().put(redisNfeKey, hash, value);
        } catch (Exception e) {
            log.error("Error acessing redis to acquire value", e);
            throw new RedisAccessException("Error acessing redis to acquire value");
        }
    }
}