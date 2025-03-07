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

    @GetMapping("/cache/status")
    public ResponseEntity<String> getCacheStatus() {
        String status = cacheInspector.getCacheStatus();
        return ResponseEntity.ok(status);
    }

}
