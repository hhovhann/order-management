package am.hhovhann.order_management.adapter.in.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "CreateOrderRequest", description = "Request object for creating an order")
public record CreateOrderRequest(
    @Schema(description = "Unique identifier of the customer", required = true) UUID customerId,
    @Schema(description = "Street address", required = true) String street,
    @Schema(description = "City", required = true) String city,
    @Schema(description = "Country", required = true) String country,
    @Schema(description = "Postal code", required = true) String postcode,
    @Schema(description = "Address number", required = true) Integer addressNumber,
    @Schema(description = "Number of pilotes", required = true) Integer numberOfPilotes,
    @Schema(description = "Price per order", required = true) BigDecimal pricePerOrder,
    @Schema(description = "Total price of the order", required = true) BigDecimal totalPrice) {}
