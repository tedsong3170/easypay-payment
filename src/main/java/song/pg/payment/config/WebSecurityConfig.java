package song.pg.payment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import song.pg.payment.config.security.JwtAuthFilter;
import song.pg.payment.config.security.MerchantAuthenticationProvider;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig
{

  private final MerchantAuthenticationProvider merchantAuthenticationProvider;
  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  @Order(1)
  public SecurityFilterChain basicChain(HttpSecurity http) throws Exception
  {
    http
      .securityMatcher("/api/payment/ready/**")
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth ->
        auth.anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults())
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(STATELESS)
      )
      .authenticationProvider(authenticationProvider());

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain jwtChain(HttpSecurity http) throws Exception
  {
    http
      .securityMatcher("/api/payment/method/**", "/api/payment/request/**")
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth ->
        auth.anyRequest().authenticated()
      )
      .httpBasic(AbstractHttpConfigurer::disable)
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(STATELESS)
      )
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider()
  {
    return merchantAuthenticationProvider;
  }
}
