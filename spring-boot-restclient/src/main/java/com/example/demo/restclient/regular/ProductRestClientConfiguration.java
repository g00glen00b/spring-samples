package com.example.demo.restclient.regular;

import com.example.demo.DummyAPIProperties;
import com.example.demo.model.DummyAPIError;
import com.example.demo.model.InvalidProductException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Configuration
public class ProductRestClientConfiguration {
    @Bean
    public RestClient dummyAPIRestClient(DummyAPIProperties properties, RestClient.Builder builder, ObjectMapper objectMapper) {
        return builder
            .baseUrl(properties.url())
            .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                DummyAPIError error = objectMapper.readValue(response.getBody(), DummyAPIError.class);
                throw new InvalidProductException(error);
            })
            .build();
    }
}
