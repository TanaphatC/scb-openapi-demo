package th.co.azay.configuration;


import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    @Qualifier("scbHttpClient")
    CloseableHttpClient scbHttpClient;

    @Bean(name = "scbClientHttpRequestFactory")
    public ClientHttpRequestFactory scbClientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(scbHttpClient);

        return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
    }

    @Bean(name = "scbRestTemplate")
    public RestTemplate scbRestTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(this::scbClientHttpRequestFactory)
                .build();
    }

}