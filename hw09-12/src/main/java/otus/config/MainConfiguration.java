package otus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import otus.cache.CacheConfigConfiguration;
import otus.cache.CacheConfiguration;
import otus.hibernate.DBServiceHibernateConfiguration;
import otus.hibernate.HibernateConfiguration;

@Configuration
@Import({CacheConfiguration.class, CacheConfigConfiguration.class, DBServiceHibernateConfiguration.class, HibernateConfiguration.class})
public class MainConfiguration {
}