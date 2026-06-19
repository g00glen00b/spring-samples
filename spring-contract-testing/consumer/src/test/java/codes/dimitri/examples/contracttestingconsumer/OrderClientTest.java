package codes.dimitri.examples.contracttestingconsumer;

import codes.dimitri.examples.contracttestingconsumer.order.OrderClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.web.client.RestClient;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class OrderClientTest {

    @RegisterExtension
    static StubRunnerExtension stubRunner = new StubRunnerExtension()
        .stubsMode(StubRunnerProperties.StubsMode.LOCAL)
        .downloadStub("codes.dimitri.examples", "spring-contract-testing");

    OrderClient client;

    @BeforeEach
    void setUp() throws URISyntaxException {
        var url = stubRunner.findStubUrl("codes.dimitri.examples", "spring-contract-testing").toURI();
        client = new OrderClient(RestClient.builder().baseUrl(url).build());
    }

    @Test
    void findAll_returnsOrders() {
        var orders = client.findAll();

        assertThat(orders).hasSize(1);
        assertThat(orders.getFirst().id()).isEqualTo(1L);
        assertThat(orders.getFirst().customerId()).isEqualTo("customer-1");
    }

    @Test
    void findById_returnsOrder_whenFound() {
        var order = client.findById(1L);

        assertThat(order).isPresent();
        assertThat(order.get().id()).isEqualTo(1L);
        assertThat(order.get().customerId()).isEqualTo("customer-1");
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        var order = client.findById(999L);

        assertThat(order).isEmpty();
    }
}
