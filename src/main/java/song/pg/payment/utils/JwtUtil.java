package song.pg.payment.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import song.pg.payment.models.customer.CustomerVo;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil
{
  private final Key key;
  private static final long expiration = 60 * 5;

  public JwtUtil(
    @Value("${jwt.secret}") String secretKey
  )
  {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String generate(CustomerVo customerVo)
  {
    Claims claims = Jwts.claims();
    claims.put("di", customerVo.getDi());

    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
      .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(expiration).toInstant()))
      .signWith(key)
      .compact();
  }

  public Claims getTokenClaims(String token)
  {
    try
    {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    catch (Exception e)
    {
      log.error("JWT 토큰 검증 실패", e);
      throw new KnownException(ExceptionEnum.INVALID_TOKEN);
    }
  }
}
