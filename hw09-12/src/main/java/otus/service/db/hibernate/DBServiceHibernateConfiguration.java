package otus.service.db.hibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import otus.cache.CacheEngine;
import otus.messageSystem.Address;
import otus.messageSystem.MessageSystemContext;
import otus.service.DBService;

@org.springframework.context.annotation.Configuration
public class DBServiceHibernateConfiguration {

    @Bean
    public DBService dbService(Configuration configuration, CacheEngine cacheEngine, @Qualifier("dbAddress") Address address, MessageSystemContext context){
        return new DBServiceHibernateImpl(configuration, cacheEngine, address, context);
    }
}
