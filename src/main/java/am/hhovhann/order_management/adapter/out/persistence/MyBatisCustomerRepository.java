package am.hhovhann.order_management.adapter.out.persistence;

import am.hhovhann.order_management.application.port.out.CustomerRepositoryPort;
import am.hhovhann.order_management.domain.model.Customer;
import am.hhovhann.order_management.adapter.out.persistence.mybatis.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MyBatisCustomerRepository implements CustomerRepositoryPort {

  private final CustomerMapper mapper;

  public MyBatisCustomerRepository(CustomerMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void save(Customer c) {
    mapper.insert(c);
  }

  @Override
  public void update(Customer c) {
    mapper.update(c);
  }

  @Override
  public void delete(UUID id) {
    mapper.delete(id);
  }

  @Override
  public Optional<Customer> findById(UUID id) {
    return mapper.findById(id);
  }

  @Override
  public List<Customer> searchByName(String name, int page, int size) {
    int offset = page * size;
    return mapper.searchByName(name, offset, size);
  }
}
