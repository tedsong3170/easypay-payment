package song.pg.payment.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnownException extends RuntimeException
{
  public final String code;
  public KnownException(ExceptionEnum e)
  {
    super(e.getMessage());
    this.code = e.getCode();
  }
}
