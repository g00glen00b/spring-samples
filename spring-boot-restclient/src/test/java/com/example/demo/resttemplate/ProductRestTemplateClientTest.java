package com.example.demo.resttemplate;

import com.example.demo.DummyAPIProperties;
import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.InvalidProductException;
import com.example.demo.model.Product;
import com.example.demo.model.UpdateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.ProductFaker.aProductJson;
import static com.example.demo.ProductFaker.aProductMatchingAGivenJSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ProductRestTemplateClientTest {
    private ProductRestTemplateClient client;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        DummyAPIProperties properties = new DummyAPIProperties("https://dummyjson.com");
        RestTemplate restTemplate = new ProductRestTemplateConfiguration().dummyAPIRestTemplate(properties, new RestTemplateBuilder());
        server = MockRestServiceServer.createServer(restTemplate);
        client = new ProductRestTemplateClient(restTemplate);
    }

    @Test
    void findById() {
        String json = aProductJson();
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/1")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        ResponseEntity<Product> result = client.findById(1);
        assertThat(result.getBody()).isEqualTo(aProductMatchingAGivenJSON());
    }



    @Test
    void findById_exception() {
        String json = """
            {
              "message": "Product with ID '1' not found"
            }
        """;
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/1")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withResourceNotFound()
                .body(json)
                .contentType(MediaType.APPLICATION_JSON));
        assertThatExceptionOfType(InvalidProductException.class)
            .isThrownBy(() -> client.findById(1))
            .withMessage("Product with ID '1' not found");
    }

    @Test
    void create() {
        String json = aProductJson();
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/add")))
            .andExpect(method(HttpMethod.POST))
            .andExpect(jsonPath("$.title", equalTo("Detonator")))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        ResponseEntity<Product> result = client.create(new CreateProductRequest("Detonator"));
        assertThat(result.getBody()).isEqualTo(aProductMatchingAGivenJSON());
    }

    @Test
    void create_exception() {
        String json = """
            {
              "message": "Product with name 'Detonator' already exists"
            }
        """;
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/add")))
            .andExpect(method(HttpMethod.POST))
            .andExpect(jsonPath("$.title", equalTo("Detonator")))
            .andRespond(withBadRequest()
                .body(json)
                .contentType(MediaType.APPLICATION_JSON));
        assertThatExceptionOfType(InvalidProductException.class)
            .isThrownBy(() -> client.create(new CreateProductRequest("Detonator")))
            .withMessage("Product with name 'Detonator' already exists");
    }

    @Test
    void update() {
        String json = aProductJson();
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/1")))
            .andExpect(method(HttpMethod.PUT))
            .andExpect(jsonPath("$.title", equalTo("Detonator")))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        ResponseEntity<Product> result = client.update(1, new UpdateProductRequest("Detonator"));
        assertThat(result.getBody()).isEqualTo(aProductMatchingAGivenJSON());
    }

    @Test
    void update_exception() {
        String json = """
            {
              "message": "Product with name 'Detonator' already exists"
            }
        """;
        server
            .expect(once(), requestTo(startsWith("https://dummyjson.com/products/1")))
            .andExpect(method(HttpMethod.PUT))
            .andExpect(jsonPath("$.title", equalTo("Detonator")))
            .andRespond(withBadRequest()
                .body(json)
                .contentType(MediaType.APPLICATION_JSON));
        assertThatExceptionOfType(InvalidProductException.class)
            .isThrownBy(() -> client.update(1, new UpdateProductRequest("Detonator")))
            .withMessage("Product with name 'Detonator' already exists");
    }
}