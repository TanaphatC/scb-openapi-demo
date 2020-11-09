package th.co.azay.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import th.co.azay.domain.adapter.ScbAdapter;
import th.co.azay.domain.adapter.ScbApiAdapter;

@Configuration
public class RepositoryConfiguration {

    @Bean
    ScbAdapter scbAdapter() {
        return new ScbApiAdapter();
    }

}
