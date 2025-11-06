package am.hhovhann.order_management.application.port.in;

import java.util.UUID;

public record UpdateCustomerCommand(
    UUID id, String firstName, String lastName, String phoneNumber, String email) {}
