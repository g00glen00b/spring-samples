package com.example.demo.resttemplate;

import com.example.demo.DummyAPIProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductRestTemplateConfiguration {
    @Bean
    public RestTemplate dummyAPIRestTemplate(DummyAPIProperties properties, RestTemplateBuilder builder) {
        return builder.rootUri(properties.url()).build();
    }
}
