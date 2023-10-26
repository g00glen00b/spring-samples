package com.example.demo.restclient.httpexchange;

import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.UpdateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface ProductRestClientExchangeClient {
    @GetExchange("/products/{id}")
    ResponseEntity<Product> findById(@PathVariable int id);

    @PostExchange("/products/add")
    ResponseEntity<Product> create(@RequestBody CreateProductRequest request);

    @PutExchange("/products/{id}")
    ResponseEntity<Product> update(@PathVariable int id, @RequestBody UpdateProductRequest request);
}
