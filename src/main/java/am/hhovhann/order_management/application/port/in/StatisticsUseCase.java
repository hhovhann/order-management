package am.hhovhann.order_management.application.port.in;

import am.hhovhann.order_management.domain.model.CustomerOrderStats;
import java.util.List;

public interface StatisticsUseCase {
  List<CustomerOrderStats> getHighValueCustomers();
}
