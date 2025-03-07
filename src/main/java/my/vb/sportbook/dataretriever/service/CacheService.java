package my.vb.sportbook.dataretriever.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearCache(Long id) {
        Cache cache = cacheManager.getCache("entitiesCache");
        if (cache != null) {
            cache.evict(id);
        }
    }
}

