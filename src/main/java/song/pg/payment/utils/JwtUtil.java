package song.pg.payment.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil
{
  public String generate()
  {
    return Jwts.builder()
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
      .signWith(Keys.hmacShaKeyFor("71d701de-96d2-4d69-a338-eb7e963e2a80".getBytes())).compact();
  }
}
