package com.llucca.arquive.worker.service.cache;

public interface CacheService {
    Object getFromChache(Object hash);

    void putToChache(Object hash, Object value);
}
