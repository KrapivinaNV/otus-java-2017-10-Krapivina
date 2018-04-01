package otus.service.frontend;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import otus.messageSystem.Address;
import otus.messageSystem.MessageSystemContext;

@Configuration
public class FrontendServiceConfiguration {
    @Bean
    public FrontendService frontendService(MessageSystemContext messageSystemContext, @Qualifier("frontAddress") Address frontAddress) {
        return new FrontendServiceImpl(messageSystemContext, frontAddress);
    }
}
