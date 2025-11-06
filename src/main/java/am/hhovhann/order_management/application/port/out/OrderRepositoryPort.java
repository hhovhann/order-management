package am.hhovhann.order_management.application.port.out;

import am.hhovhann.order_management.domain.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
  void save(Order o);

  void update(Order o);

  Optional<Order> findById(UUID id);

  List<Order> findAll(int page, int size);

  List<Order> searchByCustomerName(String name, int page, int size);
}
