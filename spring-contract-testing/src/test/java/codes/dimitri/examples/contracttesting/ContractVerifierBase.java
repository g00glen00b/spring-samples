package codes.dimitri.examples.contracttesting;

import codes.dimitri.examples.contracttesting.order.Order;
import codes.dimitri.examples.contracttesting.order.OrderController;
import codes.dimitri.examples.contracttesting.order.OrderRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
public abstract class ContractVerifierBase {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderRepository repository;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        Order order = new Order(1L, "customer-1", new BigDecimal("49.99"));

        when(repository.findAll()).thenReturn(List.of(order));
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(repository.findById(999L)).thenReturn(Optional.empty());
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(order)));
    }
}