package com.example.demo.webclient.regular;

import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.UpdateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProductWebClientClient {
    private final WebClient webClient;

    public ProductWebClientClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ResponseEntity<Product>> findById(int id) {
        Map<String, Object> variables = Map.of("id", id);
        return webClient
            .get()
            .uri(builder -> builder
                .pathSegment("products", "{id}")
                .build(variables))
            .retrieve()
            .toEntity(Product.class);
    }

    public Mono<ResponseEntity<Product>> create(CreateProductRequest request) {
        return webClient
            .post()
            .uri("/products/add")
            .bodyValue(request)
            .retrieve()
            .toEntity(Product.class);
    }

    public Mono<ResponseEntity<Product>> update(int id, UpdateProductRequest request) {
        Map<String, Object> variables = Map.of("id", id);
        return webClient
            .put()
            .uri(builder -> builder
                .pathSegment("products", "{id}")
                .build(variables))
            .bodyValue(request)
            .retrieve()
            .toEntity(Product.class);
    }
}
