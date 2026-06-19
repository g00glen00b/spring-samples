package codes.dimitri.examples.contracttestingconsumer.order;

import java.math.BigDecimal;

public record Order(Long id, String customerId, BigDecimal total) {}
