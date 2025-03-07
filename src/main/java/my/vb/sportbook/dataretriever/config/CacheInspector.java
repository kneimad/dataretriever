package my.vb.sportbook.dataretriever.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CacheInspector {

    private final HazelcastInstance hazelcastInstance;

    public CacheInspector(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public String getCacheStatus() {
        IMap<Object, Object> cacheMap = hazelcastInstance.getMap("entitiesCache");

        if (cacheMap.isEmpty()) {
            return "Cache is empty.";
        }

        String cacheContents = cacheMap.entrySet().stream()
                .map(entry -> "Key: " + entry.getKey() + ", Value: " + entry.getValue())
                .collect(Collectors.joining("\n"));

        return String.format("Cache contains %d entries:\n%s", cacheMap.size(), cacheContents);
    }

}
