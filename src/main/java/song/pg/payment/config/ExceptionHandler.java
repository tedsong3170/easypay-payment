package song.pg.payment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.utils.KnownException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler
{
  @org.springframework.web.bind.annotation.ExceptionHandler(KnownException.class)
  public CommonResponse knownExceptionHandler(KnownException e)
  {
    log.error("KnownException: {}", e.getMessage());
    return CommonResponse.builder()
      .code(e.getCode())
      .message(e.getMessage())
      .build();
  }
}
