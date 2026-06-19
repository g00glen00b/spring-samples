package codes.dimitri.examples.contracttestingconsumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.Matchers;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import codes.dimitri.examples.contracttestingconsumer.order.OrderClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@PactConsumerTest
class PactOrderClientTest {

    private static final String CONSUMER = "spring-contract-testing-consumer";
    private static final String PROVIDER = "spring-contract-testing";

    private OrderClient clientFor(MockServer mockServer) {
        return new OrderClient(RestClient.builder().baseUrl(mockServer.getUrl()).build());
    }

    @Pact(consumer = CONSUMER, provider = PROVIDER)
    V4Pact findAll(PactBuilder builder) {
        return builder
            .expectsToReceiveHttpInteraction("a request for all orders", interaction -> interaction
                .state("orders exist")
                .withRequest(request -> request
                    .method("GET")
                    .path("/orders")
                )
                .willRespondWith(response -> response
                    .status(200)
                    .header("Content-Type", Matchers.regexp("application/json.*", "application/json"))
                    .body(LambdaDsl.newJsonArrayMinLike(1, array ->
                        array.object(o -> {
                            o.integerType("id", 1);
                            o.stringType("customerId", "customer-1");
                            o.decimalType("total", new BigDecimal("49.99"));
                        })
                    ).build())
                )
            )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "findAll")
    void findAll_returnsOrders(MockServer mockServer) {
        var client = clientFor(mockServer);
        var orders = client.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.getFirst().id()).isEqualTo(1L);
        assertThat(orders.getFirst().customerId()).isEqualTo("customer-1");
    }

    @Pact(consumer = CONSUMER, provider = PROVIDER)
    V4Pact findById_found(PactBuilder builder) {
        return builder
            .expectsToReceiveHttpInteraction("a request for order 1", interaction -> interaction
                .state("order 1 exists")
                .withRequest(request -> request
                    .method("GET")
                    .path("/orders/1")
                )
                .willRespondWith(response -> response
                    .status(200)
                    .header("Content-Type", Matchers.regexp("application/json.*", "application/json"))
                    .body(LambdaDsl.newJsonBody(o -> {
                        o.integerType("id", 1);
                        o.stringType("customerId", "customer-1");
                        o.decimalType("total", new BigDecimal("49.99"));
                    }).build())
                )
            )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "findById_found")
    void findById_returnsOrder_whenFound(MockServer mockServer) {
        var client = clientFor(mockServer);
        var order = client.findById(1L);
        assertThat(order).isPresent();
        assertThat(order.get().id()).isEqualTo(1L);
        assertThat(order.get().customerId()).isEqualTo("customer-1");
        assertThat(order.get().total()).isEqualByComparingTo(new BigDecimal("49.99"));
    }

    @Pact(consumer = CONSUMER, provider = PROVIDER)
    V4Pact findById_notFound(PactBuilder builder) {
        return builder
            .expectsToReceiveHttpInteraction("a request for order 999", interaction -> interaction
                .state("order 999 does not exist")
                .withRequest(request -> request
                    .method("GET")
                    .path("/orders/999")
                )
                .willRespondWith(response -> response
                    .status(404)
                )
            )
            .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "findById_notFound")
    void findById_returnsEmpty_whenNotFound(MockServer mockServer) {
        var client = clientFor(mockServer);
        var order = client.findById(999L);
        assertThat(order).isEmpty();
    }
}
