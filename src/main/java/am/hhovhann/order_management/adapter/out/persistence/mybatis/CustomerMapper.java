package am.hhovhann.order_management.adapter.out.persistence.mybatis;

import am.hhovhann.order_management.domain.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CustomerMapper {
  void insert(Customer c);

  void update(Customer c);

  void delete(@Param("id") UUID id);

  Optional<Customer> findById(@Param("id") UUID id);

  List<Customer> searchByName(
      @Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);
}
