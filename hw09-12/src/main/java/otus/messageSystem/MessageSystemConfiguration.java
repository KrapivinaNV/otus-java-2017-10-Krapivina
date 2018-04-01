package otus.messageSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSystemConfiguration {

    @Bean
    MessageSystem messageSystemConfiguration() {
        return new MessageSystem();
    }
}
