package am.hhovhann.order_management.application.service;

import am.hhovhann.order_management.application.port.in.StatisticsUseCase;
import am.hhovhann.order_management.adapter.out.persistence.mybatis.StatisticsMapper;
import am.hhovhann.order_management.domain.model.CustomerOrderStats;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StatisticsService implements StatisticsUseCase {

  private final StatisticsMapper mapper;

  public StatisticsService(StatisticsMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public List<CustomerOrderStats> getHighValueCustomers() {
    return mapper.getHighValueCustomers();
  }
}
