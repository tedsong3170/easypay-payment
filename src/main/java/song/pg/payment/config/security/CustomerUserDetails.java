package song.pg.payment.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomerUserDetails implements UserDetails, Serializable
{
  private static final long serialVersionUID = -2515985495105983985L;

  private final String di;
  private final String mid;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return List.of(new SimpleGrantedAuthority("ACCESS_TOKEN"));
  }

  public CustomerUserDetails(final String di, final String mid)
  {
    this.di = di;
    this.mid = mid;
  }

  @Override
  public String getPassword()
  {
    return mid;
  }

  @Override
  public String getUsername()
  {
    return di;
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
