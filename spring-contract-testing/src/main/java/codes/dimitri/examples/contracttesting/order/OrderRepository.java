package codes.dimitri.examples.contracttesting.order;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private static final List<Order> ORDERS = List.of(
        new Order(1L, "customer-1", new BigDecimal("49.99")),
        new Order(2L, "customer-2", new BigDecimal("129.00")),
        new Order(3L, "customer-1", new BigDecimal("9.95"))
    );

    public List<Order> findAll() {
        return ORDERS;
    }

    public Optional<Order> findById(Long id) {
        return ORDERS.stream().filter(o -> o.id().equals(id)).findFirst();
    }
}