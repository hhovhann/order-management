package am.hhovhann.order_management.application.service;

import am.hhovhann.order_management.application.port.in.*;
import am.hhovhann.order_management.application.port.out.CustomerRepositoryPort;
import am.hhovhann.order_management.domain.exception.CustomerNotFoundException;
import am.hhovhann.order_management.domain.model.Customer;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService implements CustomerUseCase {

  private final CustomerRepositoryPort customerRepositoryPort;
  private final ProducerTemplate producerTemplate;

  public CustomerService(
      CustomerRepositoryPort customerRepositoryPort, ProducerTemplate producerTemplate) {
    this.customerRepositoryPort = customerRepositoryPort;
    this.producerTemplate = producerTemplate;
  }

  @Override
  public Customer createCustomer(CreateCustomerCommand command) {
    Customer c =
        new Customer(
            UUID.randomUUID(),
            command.firstName(),
            command.lastName(),
            command.phoneNumber(),
            command.email());
    customerRepositoryPort.save(c);
    producerTemplate.sendBody("direct:domain-events", "CustomerCreated: " + c.id());

    return c;
  }

  @Override
  public Customer updateCustomer(UpdateCustomerCommand command) {
    Customer existing =
        customerRepositoryPort
            .findById(command.id())
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        "Customer with id " + command.id() + " not found"));
    Customer updated =
        new Customer(
            existing.id(),
            command.firstName(),
            command.lastName(),
            command.phoneNumber(),
            command.email());
    customerRepositoryPort.update(updated);
    producerTemplate.sendBody("direct:domain-events", "CustomerUpdated: " + updated.id());

    return updated;
  }

  @Override
  public void deleteCustomer(UUID id) {
    customerRepositoryPort.delete(id);
  }

  @Override
  public Customer findCustomerById(UUID id) {
    return customerRepositoryPort
        .findById(id)
        .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
  }

  @Override
  public List<Customer> searchCustomersByName(String name, int page, int size) {
    return customerRepositoryPort.searchByName(name, page, size);
  }
}
