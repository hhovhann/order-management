package am.hhovhann.order_management.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateOrderCommand(
    UUID id,
    UUID customerId,
    String street,
    String city,
    String country,
    String postcode,
    Integer addressNumber,
    int numberOfPilotes,
    BigDecimal pricePerOrder) {}
