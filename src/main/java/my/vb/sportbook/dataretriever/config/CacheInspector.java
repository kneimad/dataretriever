package my.vb.sportbook.dataretriever.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CacheInspector {

    @Value("${cache.entities.name}")
    private String entitiesCacheName;

    @Value("${cache.collection-entities.name}")
    private String collectionEntitiesCacheName;

    private final HazelcastInstance hazelcastInstance;

    public CacheInspector(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public String getEntityCacheStatus() {
        IMap<Object, Object> cacheMap = hazelcastInstance.getMap(entitiesCacheName);

        if (cacheMap.isEmpty()) {
            return entitiesCacheName.concat(" is empty.");
        }

        String cacheContents = cacheMap.entrySet().stream()
                .map(entry -> "Key: " + entry.getKey() + ", Value: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return String.format("Cache contains %d entries:\n%s", cacheMap.size(), cacheContents);
    }

    public String getCollectionEntityCacheStatus() {
        IMap<Object, Object> cacheMap = hazelcastInstance.getMap(collectionEntitiesCacheName);

        if (cacheMap.isEmpty()) {
            return collectionEntitiesCacheName.concat(" is empty.");
        }

        String cacheContents = cacheMap.entrySet().stream()
                .map(entry -> "Key: " + entry.getKey() + ", Value: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return String.format("Cache contains %d entries:\n%s", cacheMap.size(), cacheContents);
    }
}
