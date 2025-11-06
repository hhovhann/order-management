package am.hhovhann.order_management.application.service;

import am.hhovhann.order_management.application.port.in.*;
import am.hhovhann.order_management.application.port.out.OrderRepositoryPort;
import am.hhovhann.order_management.domain.exception.OrderNotFoundException;
import am.hhovhann.order_management.domain.model.Order;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderUseCase {

  private final OrderRepositoryPort orderRepositoryPort;
  private final ProducerTemplate producerTemplate;

  public OrderService(OrderRepositoryPort orderRepositoryPort, ProducerTemplate producerTemplate) {
    this.orderRepositoryPort = orderRepositoryPort;
    this.producerTemplate = producerTemplate;
  }

  @Override
  public Order createOrder(CreateOrderCommand cmd) {
    UUID id = UUID.randomUUID();
    BigDecimal total = cmd.pricePerOrder().multiply(BigDecimal.valueOf(cmd.numberOfPilotes()));
    Order order =
        new Order(
            id,
            cmd.customerId(),
            cmd.street(),
            cmd.city(),
            cmd.country(),
            cmd.postcode(),
            cmd.addressNumber(),
            cmd.numberOfPilotes(),
            cmd.pricePerOrder(),
            total,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            false);
    orderRepositoryPort.save(order);
    producerTemplate.sendBody("direct:domain-events", "OrderCreated: " + order);
    return order;
  }

  @Override
  public Order updateOrder(UpdateOrderCommand updateOrderCommand) {
    Order existing =
        orderRepositoryPort
            .findById(updateOrderCommand.id())
            .orElseThrow(
                () ->
                    new OrderNotFoundException(
                        "Order with ID " + updateOrderCommand.id() + " not found"));
    BigDecimal total =
        updateOrderCommand
            .pricePerOrder()
            .multiply(BigDecimal.valueOf(updateOrderCommand.numberOfPilotes()));

    Order updated =
        new Order(
            updateOrderCommand.id(),
            updateOrderCommand.customerId(),
            updateOrderCommand.street(),
            updateOrderCommand.city(),
            updateOrderCommand.country(),
            updateOrderCommand.postcode(),
            updateOrderCommand.addressNumber(),
            updateOrderCommand.numberOfPilotes(),
            updateOrderCommand.pricePerOrder(),
            total,
            existing.creationTime(),
            OffsetDateTime.now(),
            existing.cancelled());

    orderRepositoryPort.update(updated);
    producerTemplate.sendBody("direct:domain-events", "OrderUpdated: " + updated);
    return updated;
  }

  @Override
  public Order cancelOrder(UUID orderId) {
    Order existing =
        orderRepositoryPort
            .findById(orderId)
            .orElseThrow(
                () -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

    if (existing.cancelled()) {
      throw new IllegalStateException("Order with ID " + orderId + " is already cancelled");
    }

    Order cancelled =
        new Order(
            existing.id(),
            existing.customerId(),
            existing.street(),
            existing.city(),
            existing.country(),
            existing.postcode(),
            existing.addressNumber(),
            existing.numberOfPilotes(),
            existing.pricePerOrder(),
            existing.totalPrice(),
            existing.creationTime(),
            OffsetDateTime.now(),
            true);

    orderRepositoryPort.update(cancelled);
    producerTemplate.sendBody("direct:domain-events", "OrderCancelled: " + cancelled.id());
    return cancelled;
  }

  @Override
  public List<Order> getAllOrders(int page, int size) {
    return orderRepositoryPort.findAll(page, size);
  }

  @Override
  public Order getOrderById(UUID id) {
    return orderRepositoryPort
        .findById(id)
        .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
  }

  @Override
  public List<Order> searchOrdersByName(String name, int page, int size) {
    return orderRepositoryPort.searchByCustomerName(name, page, size);
  }
}
