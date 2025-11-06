package am.hhovhann.order_management.adapter.out.persistence.mybatis;

import am.hhovhann.order_management.domain.model.CustomerOrderStats;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StatisticsMapper {
  List<CustomerOrderStats> getHighValueCustomers();
}
