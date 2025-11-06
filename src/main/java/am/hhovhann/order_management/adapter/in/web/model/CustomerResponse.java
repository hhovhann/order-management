package am.hhovhann.order_management.adapter.in.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "CustomerResponse", description = "Response object for customer details")
public record CustomerResponse(
    @Schema(description = "Unique identifier of the customer") UUID id,
    @Schema(description = "First name of the customer") String firstName,
    @Schema(description = "Last name of the customer") String lastName,
    @Schema(description = "Phone number of the customer") String phoneNumber,
    @Schema(description = "Email of the customer") String email) {}
