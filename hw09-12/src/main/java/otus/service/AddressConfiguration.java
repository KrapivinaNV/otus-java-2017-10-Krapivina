package otus.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otus.messageSystem.Address;

@Configuration
public class AddressConfiguration {

    @Bean
    @Qualifier("frontAddress")
    public Address frontAddress() {
        return new Address("frontAddress");
    }

    @Bean
    @Qualifier("dbAddress")
    public Address dbAddress() {
        return new Address("dbAddress");
    }
}
