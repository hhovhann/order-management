package am.hhovhann.order_management.application.port.in;

import am.hhovhann.order_management.domain.model.Customer;
import java.util.UUID;
import java.util.List;

public interface CustomerUseCase {
  Customer createCustomer(CreateCustomerCommand command);

  Customer updateCustomer(UpdateCustomerCommand command);

  void deleteCustomer(UUID id);

  Customer findCustomerById(UUID id);

  List<Customer> searchCustomersByName(String name, int page, int size);
}
