package am.hhovhann.order_management.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("am.hhovhann.order_management.adapter.out.persistence.mybatis")
public class MyBatisConfig {
}
