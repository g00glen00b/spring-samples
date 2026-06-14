package codes.dimitri.examples.contracttesting.order;

import java.math.BigDecimal;

public record Order(Long id, String customerId, BigDecimal total) {}
