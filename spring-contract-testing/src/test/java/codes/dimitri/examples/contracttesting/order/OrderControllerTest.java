package codes.dimitri.examples.contracttesting.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderRepository repository;

    @Test
    void findAll_returnsAllOrders() throws Exception {
        when(repository.findAll()).thenReturn(List.of(
            new Order(1L, "customer-1", new BigDecimal("49.99"))
        ));

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].customerId").value("customer-1"))
            .andExpect(jsonPath("$[0].total").value(49.99));
    }

    @Test
    void findById_returnsOrder_whenFound() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(
            new Order(1L, "customer-1", new BigDecimal("49.99"))
        ));

        mockMvc.perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.customerId").value("customer-1"))
            .andExpect(jsonPath("$.total").value(49.99));
    }

    @Test
    void findById_returns404_whenNotFound() throws Exception {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void findAllPaged_returnsPagedOrders() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(
            new PageImpl<>(List.of(new Order(1L, "customer-1", new BigDecimal("49.99"))))
        );

        mockMvc.perform(get("/orders/paged"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].id").value(1))
            .andExpect(jsonPath("$.content[0].customerId").value("customer-1"))
            .andExpect(jsonPath("$.totalElements").value(1));
    }
}
