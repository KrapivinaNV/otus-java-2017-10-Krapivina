package otus.service.db.hibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfiguration {

    @Bean
    public Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        return configuration;
    }
}
