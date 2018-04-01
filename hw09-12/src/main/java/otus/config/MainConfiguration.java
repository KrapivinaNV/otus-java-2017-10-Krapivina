package otus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import otus.cache.CacheConfigConfiguration;
import otus.cache.CacheConfiguration;
import otus.messageSystem.MessageSystemConfiguration;
import otus.messageSystem.MessageSystemContextConfiguration;
import otus.service.AddressConfiguration;
import otus.service.db.hibernate.DBServiceHibernateConfiguration;
import otus.service.db.hibernate.HibernateConfiguration;
import otus.service.frontend.FrontendServiceConfiguration;

@Configuration
@Import({
        CacheConfiguration.class,
        CacheConfigConfiguration.class,
        DBServiceHibernateConfiguration.class,
        HibernateConfiguration.class,
        AddressConfiguration.class,
        FrontendServiceConfiguration.class,
        MessageSystemConfiguration.class,
        MessageSystemContextConfiguration.class
})
public class MainConfiguration {
}