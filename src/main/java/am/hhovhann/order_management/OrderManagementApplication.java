package am.hhovhann.order_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Order Management System.
 *
 * Application Architecture:
 * - Hexagonal Architecture (Ports and Adapters)
 * - Domain-Driven Design principles
 * - SOLID principles
 * - Clean Architecture
 *
 * Technology Stack:
 * - Spring Boot 3.5.7
 * - MyBatis for persistence
 * - Apache Camel 4.15.0 for integration
 * - OpenAPI for API documentation
 * - H2 Database (development)
 *
 * @author Hayk Hovhannisyan - hhovhann
 */
//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "am.hhovhann.order_management")
public class OrderManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderManagementApplication.class, args);
  }

}
