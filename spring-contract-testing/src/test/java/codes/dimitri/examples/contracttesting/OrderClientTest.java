package codes.dimitri.examples.contracttesting;

import codes.dimitri.examples.contracttesting.order.Order;
import codes.dimitri.examples.contracttesting.order.OrderClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
    ids = "codes.dimitri.examples:spring-contract-testing:+:stubs",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class OrderClientTest {

    @Autowired
    StubFinder stubFinder;

    OrderClient client;

    @BeforeEach
    void setUp() {
        int port = stubFinder.findStubUrl(null, "spring-contract-testing").getPort();
        client = new OrderClient(RestClient.builder().baseUrl("http://localhost:" + port).build());
    }

    @Test
    void findAll_returnsOrders() {
        List<Order> orders = client.findAll();

        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getId()).isEqualTo(1L);
        assertThat(orders.get(0).getCustomerId()).isEqualTo("customer-1");
    }

    @Test
    void findById_returnsOrder_whenFound() {
        Optional<Order> order = client.findById(1L);

        assertThat(order).isPresent();
        assertThat(order.get().getId()).isEqualTo(1L);
        assertThat(order.get().getCustomerId()).isEqualTo("customer-1");
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        Optional<Order> order = client.findById(999L);

        assertThat(order).isEmpty();
    }
}
