package my.vb.sportbook.dataretriever.controller;

import my.vb.sportbook.dataretriever.config.CacheInspector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    private final CacheInspector cacheInspector;

    public CacheController(CacheInspector cacheInspector) {
        this.cacheInspector = cacheInspector;
    }

    @GetMapping("/entitiesCache/status")
    public ResponseEntity<String> getCacheStatus() {
        String status = cacheInspector.getEntityCacheStatus();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/collectionEntitiesCache/status")
    public ResponseEntity<String> getCollectionEntityCacheStatus() {
        String status = cacheInspector.getCollectionEntityCacheStatus();
        return ResponseEntity.ok(status);
    }

}
