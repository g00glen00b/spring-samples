package com.example.demo.webclient.httpexchange;

import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.UpdateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import reactor.core.publisher.Mono;

public interface ProductWebClientExchangeClient {
    @GetExchange("/products/{id}")
    Mono<ResponseEntity<Product>> findById(@PathVariable int id);

    @PostExchange("/products/add")
    Mono<ResponseEntity<Product>> create(@RequestBody CreateProductRequest request);

    @PutExchange("/products/{id}")
    Mono<ResponseEntity<Product>> update(@PathVariable int id, @RequestBody UpdateProductRequest request);
}
