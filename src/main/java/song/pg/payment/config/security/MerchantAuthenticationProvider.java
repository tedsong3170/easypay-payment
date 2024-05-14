package song.pg.payment.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MerchantAuthenticationProvider implements AuthenticationProvider {
  private final MerchantUserDetailService merchantUserDetailService;

  public MerchantAuthenticationProvider(MerchantUserDetailService merchantUserDetailService) {
    this.merchantUserDetailService = merchantUserDetailService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.debug("Basic 인증시작");

    String mid = authentication.getName();
    String secretKey = authentication.getCredentials().toString();

    UserDetails userDetails = merchantUserDetailService.loadUserByUsername(mid);

    if (userDetails.getPassword().equals(secretKey)) {
      return new UsernamePasswordAuthenticationToken(userDetails, secretKey, userDetails.getAuthorities());
    }
    else
    {
      throw new BadCredentialsException("Bad credentials");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
