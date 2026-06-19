package codes.dimitri.examples.contracttesting;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.spring7.PactVerificationSpring7Provider;
import au.com.dius.pact.provider.spring.spring7.Spring7MockMvcTestTarget;
import codes.dimitri.examples.contracttesting.order.Order;
import codes.dimitri.examples.contracttesting.order.OrderController;
import codes.dimitri.examples.contracttesting.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Provider("spring-contract-testing")
@PactBroker(url = "${pact.broker.url:http://localhost:9292}")
@WebMvcTest(OrderController.class)
class PactOrderVerificationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderRepository repository;

    @BeforeEach
    void setUp(PactVerificationContext context) {
        context.setTarget(new Spring7MockMvcTestTarget(mockMvc));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpring7Provider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("orders exist")
    void ordersExist() {
        var order = new Order(1L, "customer-1", new BigDecimal("49.99"));
        when(repository.findAll()).thenReturn(List.of(order));
    }

    @State("order 1 exists")
    void order1Exists() {
        var order = new Order(1L, "customer-1", new BigDecimal("49.99"));
        when(repository.findById(1L)).thenReturn(Optional.of(order));
    }

    @State("order 999 does not exist")
    void order999DoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
    }
}
