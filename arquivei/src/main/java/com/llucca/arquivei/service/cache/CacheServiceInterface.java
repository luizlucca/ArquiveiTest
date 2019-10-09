package com.llucca.arquivei.service.cache;

public interface CacheServiceInterface {
    Object getFromChache(Object hash);
    void putToChache(Object hash, Object value);
}
