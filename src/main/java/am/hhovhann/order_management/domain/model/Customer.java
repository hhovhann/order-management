package am.hhovhann.order_management.domain.model;

import java.util.UUID;

public record Customer(
    UUID id, String firstName, String lastName, String phoneNumber, String email) {}
