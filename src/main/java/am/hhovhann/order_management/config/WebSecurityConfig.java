package am.hhovhann.order_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User.withUsername("user").password("{noop}user123").roles("USER").build();

    UserDetails admin =
        User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing
        .authorizeHttpRequests(
            authz ->
                authz
                    .requestMatchers(
                        "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/v1/**")
                    .authenticated())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
