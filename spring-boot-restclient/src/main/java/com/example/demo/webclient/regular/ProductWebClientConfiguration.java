package com.example.demo.webclient.regular;

import com.example.demo.DummyAPIProperties;
import com.example.demo.model.DummyAPIError;
import com.example.demo.model.InvalidProductException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class ProductWebClientConfiguration {
    @Bean
    public WebClient dummyAPIWebClient(DummyAPIProperties properties, WebClient.Builder builder) {
        return builder
            .baseUrl(properties.url())
            .defaultStatusHandler(HttpStatusCode::is4xxClientError, response -> response
                .bodyToMono(DummyAPIError.class)
                .map(InvalidProductException::new)
                .flatMap(Mono::error))
            .build();
    }
}
