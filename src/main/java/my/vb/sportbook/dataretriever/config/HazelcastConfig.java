package my.vb.sportbook.dataretriever.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import my.vb.sportbook.dataretriever.dto.EventDTO;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    private static final String CACHE_NAME = "entitiesCache";
    private static final int TTL_SECONDS = 15;

    @Bean
    public com.hazelcast.core.HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.addMapConfig(createEntitiesCacheConfig());

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
                .setName(CACHE_NAME)
                .setTimeToLiveSeconds(TTL_SECONDS);
    }
}


