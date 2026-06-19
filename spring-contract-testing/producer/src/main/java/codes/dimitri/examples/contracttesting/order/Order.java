package codes.dimitri.examples.contracttesting.order;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private BigDecimal total;

    protected Order() {}

    public Order(Long id, String customerId, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
    }

    public Long getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getTotal() { return total; }
}
