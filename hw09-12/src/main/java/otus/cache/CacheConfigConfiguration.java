package otus.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfigConfiguration {

    private static final int MAX_ELEMENTS = 3;
    private static final boolean ETERNAL = true;

    @Bean
    public CacheConfig cacheConfigConfiguration(){
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setMaxElements(MAX_ELEMENTS);
        cacheConfig.setEternal(ETERNAL);
        return cacheConfig;
    }
}