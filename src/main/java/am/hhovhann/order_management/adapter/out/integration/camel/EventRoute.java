package am.hhovhann.order_management.adapter.out.integration.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventRoute extends RouteBuilder {
  @Override
  public void configure() {
    from("direct:domain-events")
        .log("Domain event received: ${body}")
        .to("log:domain-event-logger");
  }
}
