package am.hhovhann.order_management.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomerOrderStats(
    UUID customerId, String customerName, Long totalOrders, BigDecimal orderTotalAmount) {}
