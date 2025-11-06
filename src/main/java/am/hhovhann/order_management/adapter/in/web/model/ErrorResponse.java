package am.hhovhann.order_management.adapter.in.web.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse")
public record ErrorResponse(
        @Schema(required = true) LocalDateTime timestamp,
        @Schema(required = true) int status,
        @Schema(required = true) String error,
        @Schema(required = true) String message,
        @Schema(required = true) String path
) {}
