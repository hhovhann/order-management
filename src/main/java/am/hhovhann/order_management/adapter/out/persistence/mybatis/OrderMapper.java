package am.hhovhann.order_management.adapter.out.persistence.mybatis;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import am.hhovhann.order_management.domain.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
  void insert(Order order);

  void update(Order order);

  Optional<Order> findById(@Param("id") UUID id);

  List<Order> findAll(@Param("offset") int offset, @Param("limit") int limit);

  List<Order> searchByCustomerName(
      @Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);
}
