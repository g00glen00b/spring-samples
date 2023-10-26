package com.example.demo.webclient.httpexchange;

import com.example.demo.DummyAPIProperties;
import com.example.demo.model.CreateProductRequest;
import com.example.demo.model.UpdateProductRequest;
import com.example.demo.webclient.regular.ProductWebClientConfiguration;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

import static com.example.demo.ProductFaker.aProductJson;
import static com.example.demo.ProductFaker.aProductMatchingAGivenJSON;
import static org.assertj.core.api.Assertions.assertThat;

class ProductWebClientExchangeClientTest {
    private final BasicJsonTester jsonTester = new BasicJsonTester(getClass());
    private ProductWebClientExchangeClient client;
    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        DummyAPIProperties properties = new DummyAPIProperties(server.url("/").toString());
        WebClient webClient = new ProductWebClientConfiguration().dummyAPIWebClient(properties, WebClient.builder());
        client = new ProductWebClientHttpExchangeConfiguration().productWebClientExchangeClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void findById() throws InterruptedException {
        server.enqueue(new MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(aProductJson()));
        StepVerifier
            .create(client.findById(1))
            .assertNext(entity -> assertThat(entity.getBody()).isEqualTo(aProductMatchingAGivenJSON()))
            .verifyComplete();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/1");
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void findById_exception() throws InterruptedException {
        String json = """
            {
              "message": "Product with ID '1' not found"
            }
        """;
        server.enqueue(new MockResponse()
            .setResponseCode(404)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(json));
        StepVerifier
            .create(client.findById(1))
            .expectErrorMessage("Product with ID '1' not found")
            .verify();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/1");
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @Test
    void create() throws InterruptedException {
        server.enqueue(new MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(aProductJson()));
        StepVerifier
            .create(client.create(new CreateProductRequest("Detonator")))
            .assertNext(entity -> assertThat(entity.getBody()).isEqualTo(aProductMatchingAGivenJSON()))
            .verifyComplete();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/add");
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(jsonTester.from(request.getBody().readUtf8())).hasJsonPathValue("$.title", "Detonator");
    }

    @Test
    void create_exception() throws InterruptedException {
        String json = """
            {
              "message": "Product with name 'Detonator' already exists"
            }
        """;
        server.enqueue(new MockResponse()
            .setResponseCode(400)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(json));
        StepVerifier
            .create(client.create(new CreateProductRequest("Detonator")))
            .expectErrorMessage("Product with name 'Detonator' already exists")
            .verify();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/add");
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(jsonTester.from(request.getBody().readUtf8())).hasJsonPathValue("$.title", "Detonator");
    }

    @Test
    void update() throws InterruptedException {
        server.enqueue(new MockResponse()
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(aProductJson()));
        StepVerifier
            .create(client.update(1, new UpdateProductRequest("Detonator")))
            .assertNext(entity -> assertThat(entity.getBody()).isEqualTo(aProductMatchingAGivenJSON()))
            .verifyComplete();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/1");
        assertThat(request.getMethod()).isEqualTo("PUT");
        assertThat(jsonTester.from(request.getBody().readUtf8())).hasJsonPathValue("$.title", "Detonator");
    }

    @Test
    void update_exception() throws InterruptedException {
        String json = """
            {
              "message": "Product with name 'Detonator' already exists"
            }
        """;
        server.enqueue(new MockResponse()
            .setResponseCode(400)
            .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .setBody(json));
        StepVerifier
            .create(client.update(1, new UpdateProductRequest("Detonator")))
            .expectErrorMessage("Product with name 'Detonator' already exists")
            .verify();
        RecordedRequest request = server.takeRequest();
        assertThat(request.getPath()).isEqualTo("/products/1");
        assertThat(request.getMethod()).isEqualTo("PUT");
        assertThat(jsonTester.from(request.getBody().readUtf8())).hasJsonPathValue("$.title", "Detonator");
    }
}