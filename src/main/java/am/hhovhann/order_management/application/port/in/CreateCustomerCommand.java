package am.hhovhann.order_management.application.port.in;

public record CreateCustomerCommand(
    String firstName, String lastName, String phoneNumber, String email) {}
