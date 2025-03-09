package my.vb.sportbook.dataretriever.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import my.vb.sportbook.dataretriever.config.CacheInspector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Cache Controller", description = "Endpoints for inspecting cache status")
public class CacheController {

    private final CacheInspector cacheInspector;

    public CacheController(CacheInspector cacheInspector) {
        this.cacheInspector = cacheInspector;
    }

    @GetMapping("/entitiesCache/status")
    @Operation(
            summary = "Get Entities Cache Status",
            description = "Fetches the status and contents of the entities cache."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Returns the status of the entities cache as a string."
    )
    public ResponseEntity<String> getCacheStatus() {
        String status = cacheInspector.getEntityCacheStatus();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/collectionEntitiesCache/status")
    @Operation(
            summary = "Get Collection Entities Cache Status",
            description = "Fetches the status and contents of the collection entities cache."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Returns the status of the collection entities cache as a string."
    )
    public ResponseEntity<String> getCollectionEntityCacheStatus() {
        String status = cacheInspector.getCollectionEntityCacheStatus();
        return ResponseEntity.ok(status);
    }

}
