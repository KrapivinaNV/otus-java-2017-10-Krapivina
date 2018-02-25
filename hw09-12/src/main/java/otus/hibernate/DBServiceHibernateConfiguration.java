package otus.hibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import otus.cache.CacheEngine;
import otus.common.DBService;

@org.springframework.context.annotation.Configuration
public class DBServiceHibernateConfiguration {

    @Bean
    public DBService dbService(Configuration configuration, CacheEngine cacheEngine){
        return new DBServiceHibernateImpl(configuration, cacheEngine);
    }
}
