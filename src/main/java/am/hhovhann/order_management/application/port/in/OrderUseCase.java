package am.hhovhann.order_management.application.port.in;

import am.hhovhann.order_management.domain.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderUseCase {
  List<Order> getAllOrders(int page, int size);

  Order getOrderById(UUID id);

  List<Order> searchOrdersByName(String name, int page, int size);

  Order createOrder(CreateOrderCommand cmd);

  Order updateOrder(UpdateOrderCommand cmd);

  Order cancelOrder(UUID id);
}
