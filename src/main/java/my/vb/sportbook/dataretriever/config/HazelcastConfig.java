package my.vb.sportbook.dataretriever.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import my.vb.sportbook.dataretriever.dto.EventDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Value("${cache.entities.name}")
    private String entitiesCacheName;

    @Value("${cache.collection-entities.name}")
    private String collectionEntitiesCacheName;

    @Value("${cache.ttl.seconds}")
    private int ttlSeconds;


    @Bean
    public com.hazelcast.core.HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.addMapConfig(createEntitiesCacheConfig())
              .addMapConfig(createCollectionEntitiesCacheConfig());

        SerializerConfig serializerConfig = new SerializerConfig()
                .setTypeClass(EventDTO.class)
                .setImplementation(new EventDTOSerializer());

        config.getSerializationConfig().addSerializerConfig(serializerConfig);

        return com.hazelcast.core.Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public CacheManager cacheManager(com.hazelcast.core.HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }

    private MapConfig createEntitiesCacheConfig() {
        return new MapConfig()
                .setName(entitiesCacheName)
                .setTimeToLiveSeconds(ttlSeconds);
    }

    private MapConfig createCollectionEntitiesCacheConfig() {
        return new MapConfig()
                .setName(collectionEntitiesCacheName)
                .setTimeToLiveSeconds(ttlSeconds);
    }
}


