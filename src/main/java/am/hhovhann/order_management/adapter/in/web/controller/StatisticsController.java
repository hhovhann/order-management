package am.hhovhann.order_management.adapter.in.web.controller;

import am.hhovhann.order_management.application.port.in.StatisticsUseCase;
import am.hhovhann.order_management.domain.model.CustomerOrderStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@Tag(name = "Statistics Controller", description = "Customer Controller API")
public class StatisticsController {

  private final StatisticsUseCase service;

  public StatisticsController(StatisticsUseCase service) {
    this.service = service;
  }

  @Operation(
      summary = "List high-value customers (orders > 2 and total > 700)",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved high-value customers",
            content =
                @Content(
                    mediaType = "application/json",
                    array =
                        @ArraySchema(schema = @Schema(implementation = CustomerOrderStats.class)))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/orders")
  public List<CustomerOrderStats> getHighValueCustomers() {
    return service.getHighValueCustomers();
  }
}
