package am.hhovhann.order_management.adapter.in.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(name = "OrderResponse", description = "Response object for order details")
public record OrderResponse(
    @Schema(description = "Unique identifier of the order") UUID id,
    @Schema(description = "Unique identifier of the customer") UUID customerId,
    @Schema(description = "Street address") String street,
    @Schema(description = "City") String city,
    @Schema(description = "Country") String country,
    @Schema(description = "Postal code") String postcode,
    @Schema(description = "Address number") Integer addressNumber,
    @Schema(description = "Number of pilotes") Integer numberOfPilotes,
    @Schema(description = "Price per order") BigDecimal pricePerOrder,
    @Schema(description = "Total price of the order") BigDecimal totalPrice,
    @Schema(description = "Creation timestamp") OffsetDateTime creationTime,
    @Schema(description = "Last update timestamp") OffsetDateTime updateTime,
    @Schema(description = "Cancellation status") Boolean canceled) {}
