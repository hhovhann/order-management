package am.hhovhann.order_management.application.port.out;

import am.hhovhann.order_management.domain.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryPort {
  void save(Customer c);

  void update(Customer c);

  void delete(UUID id);

  Optional<Customer> findById(UUID id);

  List<Customer> searchByName(String name, int page, int size);
}
