package song.pg.payment.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import song.pg.payment.models.merchant.MerchantInfoVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
public class MerchantUserDetails implements UserDetails, Serializable
{
  private static final long serialVersionUID = 7462953309520233215L;
  private final MerchantInfoVo merchantInfoVo;

  public MerchantUserDetails(MerchantInfoVo merchantInfoVo)
  {
    this.merchantInfoVo = merchantInfoVo;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return List.of(new SimpleGrantedAuthority("BASIC"));
  }

  @Override
  public String getPassword()
  {
    return merchantInfoVo.getSecretKey();
  }

  @Override
  public String getUsername()
  {
    return merchantInfoVo.getMid();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }
}
