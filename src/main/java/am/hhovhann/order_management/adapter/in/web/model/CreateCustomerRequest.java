package am.hhovhann.order_management.adapter.in.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "CreateCustomerRequest", description = "Request object for creating a customer")
public record CreateCustomerRequest(
    @Schema(description = "First name of the customer", required = true) @NotBlank String firstName,
    @Schema(description = "Last name of the customer", required = true)
        @NotBlank(message = "Last name is mandatory")
        String lastName,
    @Schema(description = "Phone number of the customer", required = true)
        @NotBlank(message = "Phone number is mandatory")
        @Size(min = 10, max = 15)
        @Pattern(regexp = "\\+?[0-9]+")
        String phoneNumber,
    @Schema(description = "Email of the customer", required = true)
        @Email(message = "Invalid email format")
        String email) {}
