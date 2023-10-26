package com.example.demo.restclient.regular;

import com.example.demo.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class ProductRestClientClient {
    private final RestClient restClient;

    public ProductRestClientClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<Product> findById(int id) {
        Map<String, Object> variables = Map.of("id", id);
        return restClient
            .get()
            .uri(builder -> builder
                .pathSegment("products", "{id}")
                .build(variables))
            .retrieve()
            .toEntity(Product.class);
    }

    public ResponseEntity<Product> create(CreateProductRequest request) {
        return restClient
            .post()
            .uri("/products/add")
            .body(request)
            .retrieve()
            .toEntity(Product.class);
    }

    public ResponseEntity<Product> update(int id, UpdateProductRequest request) {
        Map<String, Object> variables = Map.of("id", id);
        return restClient
            .put()
            .uri(builder -> builder
                .pathSegment("products", "{id}")
                .build(variables))
            .body(request)
            .retrieve()
            .toEntity(Product.class);
    }
}
