package am.hhovhann.order_management.adapter.in.web.controller;

import am.hhovhann.order_management.adapter.in.web.model.CreateOrderRequest;
import am.hhovhann.order_management.adapter.in.web.model.OrderResponse;
import am.hhovhann.order_management.application.port.in.*;
import am.hhovhann.order_management.domain.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders Controller", description = "Orders Controller API")
public class OrderController {

  private final OrderUseCase orderService;

  public OrderController(OrderUseCase orderService) {
    this.orderService = orderService;
  }

  @Operation(
      summary = "Create new order",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Order created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest req) {
    var createOrderCommand =
        new CreateOrderCommand(
            req.customerId(),
            req.street(),
            req.city(),
            req.country(),
            req.postcode(),
            req.addressNumber(),
            req.numberOfPilotes(),
            req.pricePerOrder());
    Order created = orderService.createOrder(createOrderCommand);

    OrderResponse orderResponse =
        new OrderResponse(
            created.id(),
            created.customerId(),
            created.street(),
            created.city(),
            created.country(),
            created.postcode(),
            created.addressNumber(),
            created.numberOfPilotes(),
            created.pricePerOrder(),
            created.totalPrice(),
            created.creationTime(),
            created.updateTime(),
            created.cancelled());

    return ResponseEntity.created(URI.create("/api/v1/orders/" + created.id())).body(orderResponse);
  }

  @Operation(
      summary = "List all orders (pageable)",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved orders",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseEntity<List<OrderResponse>> list(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    List<Order> allOrders = orderService.getAllOrders(page, size);
    List<OrderResponse> orderResponses = new ArrayList<>();
    allOrders.forEach(
        order ->
            orderResponses.add(
                new OrderResponse(
                    order.id(),
                    order.customerId(),
                    order.street(),
                    order.city(),
                    order.country(),
                    order.postcode(),
                    order.addressNumber(),
                    order.numberOfPilotes(),
                    order.pricePerOrder(),
                    order.totalPrice(),
                    order.creationTime(),
                    order.updateTime(),
                    order.cancelled())));

    return ResponseEntity.ok(orderResponses);
  }

  @Operation(
      summary = "Get order by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved order",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
    Order order = orderService.getOrderById(id);

    OrderResponse orderResponse =
        new OrderResponse(
            order.id(),
            order.customerId(),
            order.street(),
            order.city(),
            order.country(),
            order.postcode(),
            order.addressNumber(),
            order.numberOfPilotes(),
            order.pricePerOrder(),
            order.totalPrice(),
            order.creationTime(),
            order.updateTime(),
            order.cancelled());

    return ResponseEntity.ok(orderResponse);
  }

  @Operation(
      summary = "Search orders by customer name",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved orders",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No orders found for the given name"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/search")
  public ResponseEntity<List<OrderResponse>> search(
      @RequestParam String name,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    List<Order> list = orderService.searchOrdersByName(name, page, size);

    List<OrderResponse> orderResponses = new ArrayList<>();
    list.forEach(
        order ->
            orderResponses.add(
                new OrderResponse(
                    order.id(),
                    order.customerId(),
                    order.street(),
                    order.city(),
                    order.country(),
                    order.postcode(),
                    order.addressNumber(),
                    order.numberOfPilotes(),
                    order.pricePerOrder(),
                    order.totalPrice(),
                    order.creationTime(),
                    order.updateTime(),
                    order.cancelled())));

    return ResponseEntity.ok(orderResponses);
  }

  @Operation(
      summary = "Update existing order",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Order updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request body"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PutMapping("/{id}")
  public ResponseEntity<OrderResponse> update(
      @PathVariable UUID id, @RequestBody CreateOrderRequest req) {
    var createOrderCommand =
        new UpdateOrderCommand(
            id,
            req.customerId(),
            req.street(),
            req.city(),
            req.country(),
            req.postcode(),
            req.addressNumber(),
            req.numberOfPilotes(),
            req.pricePerOrder());

    Order order = orderService.updateOrder(createOrderCommand);
    OrderResponse orderResponse =
        new OrderResponse(
            order.id(),
            order.customerId(),
            order.street(),
            order.city(),
            order.country(),
            order.postcode(),
            order.addressNumber(),
            order.numberOfPilotes(),
            order.pricePerOrder(),
            order.totalPrice(),
            order.creationTime(),
            order.updateTime(),
            order.cancelled());

    return ResponseEntity.ok(orderResponse);
  }

  @Operation(
      summary = "Cancel order by ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Order cancelled successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PatchMapping("/{id}/cancel")
  public ResponseEntity<OrderResponse> cancel(@PathVariable UUID id) {
    Order order = orderService.cancelOrder(id);
    OrderResponse orderResponse =
        new OrderResponse(
            order.id(),
            order.customerId(),
            order.street(),
            order.city(),
            order.country(),
            order.postcode(),
            order.addressNumber(),
            order.numberOfPilotes(),
            order.pricePerOrder(),
            order.totalPrice(),
            order.creationTime(),
            order.updateTime(),
            order.cancelled());

    return ResponseEntity.ok(orderResponse);
  }
}
