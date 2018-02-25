package otus.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otus.data.DataSet;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheEngine<Long, DataSet> cacheConfiguration(CacheConfig cacheConfig) {
        return new CacheEngineImpl<>(cacheConfig);
    }
}
