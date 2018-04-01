package otus.messageSystem;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSystemContextConfiguration {

    @Bean
    MessageSystemContext messageSystemContext(
            MessageSystem messageSystem,
            @Qualifier("frontAddress") Address frontAddress,
            @Qualifier("dbAddress") Address dbAddress
    ) {
        return new MessageSystemContext(messageSystem, frontAddress, dbAddress);
    }
}
