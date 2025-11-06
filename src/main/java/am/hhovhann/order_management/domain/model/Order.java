package am.hhovhann.order_management.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Order(
    UUID id,
    UUID customerId,
    String street,
    String city,
    String country,
    String postcode,
    Integer addressNumber,
    Integer numberOfPilotes,
    BigDecimal pricePerOrder,
    BigDecimal totalPrice,
    OffsetDateTime creationTime,
    OffsetDateTime updateTime,
    Boolean cancelled) {}
