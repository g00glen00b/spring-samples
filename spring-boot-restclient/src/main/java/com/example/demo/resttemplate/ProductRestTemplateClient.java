package com.example.demo.resttemplate;

import com.example.demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ProductRestTemplateClient {
    private final RestTemplate restTemplate;

    public ProductRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Product> findById(int id) {
        Map<String, Object> variables = Map.of("id", id);
        try {
            return restTemplate.getForEntity("/products/{id}", Product.class, variables);
        } catch (HttpClientErrorException ex) {
            DummyAPIError error = ex.getResponseBodyAs(DummyAPIError.class);
            throw new InvalidProductException(error);
        }
    }

    public ResponseEntity<Product> create(CreateProductRequest request) {
        try {
            return restTemplate.postForEntity("/products/add", request, Product.class);
        } catch (HttpClientErrorException ex) {
            DummyAPIError error = ex.getResponseBodyAs(DummyAPIError.class);
            throw new InvalidProductException(error);
        }
    }

    public ResponseEntity<Product> update(int id, UpdateProductRequest request) {
        Map<String, Object> variables = Map.of("id", id);
        HttpEntity<UpdateProductRequest> httpEntity = new HttpEntity<>(request, null);
        try {
            return restTemplate.exchange("/products/{id}", HttpMethod.PUT, httpEntity, Product.class, variables);
        } catch (HttpClientErrorException ex) {
            DummyAPIError error = ex.getResponseBodyAs(DummyAPIError.class);
            throw new InvalidProductException(error);
        }
    }
}
