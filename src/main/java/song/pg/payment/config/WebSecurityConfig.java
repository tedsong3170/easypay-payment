package song.pg.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import song.pg.payment.api.security.MerchantAuthenticationProvider;
import song.pg.payment.api.security.MerchantUserDetailService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig
{


  private final MerchantUserDetailService merchantUserDetailService;
  private final MerchantAuthenticationProvider merchantAuthenticationProvider;

  public WebSecurityConfig(MerchantUserDetailService merchantUserDetailService,  MerchantAuthenticationProvider merchantAuthenticationProvider) {
    this.merchantUserDetailService = merchantUserDetailService;
    this.merchantAuthenticationProvider = merchantAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
  {
    http.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth ->
        auth.anyRequest().authenticated()

      )
      .httpBasic(Customizer.withDefaults())
      .authenticationProvider(authenticationProvider());

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider()
  {
    return merchantAuthenticationProvider;
  }
}
