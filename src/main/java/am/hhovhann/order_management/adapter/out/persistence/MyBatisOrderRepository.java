package am.hhovhann.order_management.adapter.out.persistence;

import am.hhovhann.order_management.adapter.out.persistence.mybatis.OrderMapper;
import am.hhovhann.order_management.application.port.out.OrderRepositoryPort;
import am.hhovhann.order_management.domain.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MyBatisOrderRepository implements OrderRepositoryPort {

    private final OrderMapper mapper;

    public MyBatisOrderRepository(OrderMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void save(Order order) {
        mapper.insert(order);
    }

    @Override
    public void update(Order order) {
        mapper.update(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return mapper.findById(id);
    }

    @Override
    public List<Order> findAll(int page, int size) {
        int offset = page * size;
        return mapper.findAll(offset, size);
    }

    @Override
    public List<Order> searchByCustomerName(String name, int page, int size) {
        int offset = page * size;
        return mapper.searchByCustomerName(name, offset, size);
    }
}
