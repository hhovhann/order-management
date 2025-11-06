package am.hhovhann.order_management.adapter.in.web.controller;

import am.hhovhann.order_management.adapter.in.web.model.*;
import am.hhovhann.order_management.application.port.in.*;
import am.hhovhann.order_management.domain.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers Controller", description = "Customer Controller API")
public class CustomerController {

  private final CustomerUseCase service;

  public CustomerController(CustomerUseCase service) {
    this.service = service;
  }

  @Operation(
      summary = "Create a new customer",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Customer created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseEntity<CustomerResponse> create(@RequestBody CreateCustomerRequest req) {
    var createCustomerCommand =
        new CreateCustomerCommand(req.firstName(), req.lastName(), req.phoneNumber(), req.email());
    Customer created = service.createCustomer(createCustomerCommand);
    var resp =
        new CustomerResponse(
            created.id(),
            created.firstName(),
            created.lastName(),
            created.phoneNumber(),
            created.email());
    return ResponseEntity.created(URI.create("/api/v1/customers/" + created.id())).body(resp);
  }

  @Operation(
      summary = "Get customer by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved customer",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponse> getById(@PathVariable UUID id) {
    Customer c = service.findCustomerById(id);
    return ResponseEntity.ok(
        new CustomerResponse(c.id(), c.firstName(), c.lastName(), c.phoneNumber(), c.email()));
  }

  @Operation(
      summary = "Search customers by name, email, or phone number",
      description = "Search for customers using a query string. Supports pagination.",
      parameters = {
        @Parameter(
            name = "q",
            description = "Search query (matches name, email, or phone number)",
            example = "John",
            required = false),
        @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
        @Parameter(name = "size", description = "Number of items per page", example = "10")
      },
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved customers matching the search criteria",
            content =
                @Content(
                    mediaType = "application/json",
                    array =
                        @ArraySchema(schema = @Schema(implementation = CustomerResponse.class)))),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
        @ApiResponse(
            responseCode = "404",
            description = "No customers found matching the search criteria"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseEntity<List<CustomerResponse>> search(
      @RequestParam(required = false) String q,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    var list =
        service.searchCustomersByName(q == null ? "" : q, page, size).stream()
            .map(
                c ->
                    new CustomerResponse(
                        c.id(), c.firstName(), c.lastName(), c.phoneNumber(), c.email()))
            .toList();
    return ResponseEntity.ok(list);
  }

  @Operation(
      summary = "Update customer by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Customer updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PutMapping("/{id}")
  public ResponseEntity<CustomerResponse> update(
      @PathVariable UUID id, @Valid @RequestBody CreateCustomerRequest req) {
    var cmd =
        new UpdateCustomerCommand(
            id, req.firstName(), req.lastName(), req.phoneNumber(), req.email());
    Customer updated = service.updateCustomer(cmd);
    return ResponseEntity.ok(
        new CustomerResponse(
            updated.id(),
            updated.firstName(),
            updated.lastName(),
            updated.phoneNumber(),
            updated.email()));
  }

  @Operation(
      summary = "Delete customer by ID",
      responses = {
        @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }
}
