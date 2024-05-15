package song.pg.payment.utils;

import lombok.Getter;

@Getter
public enum ExceptionEnum
{
  ALREADY_EXIST_PAYMENT(400, "1001", "이미 결제가 진행중인 주문입니다."),
  UNKNOWN_PAYMENT_METHOD(500, "1002", "알 수 없는 결제 수단입니다."),
  INVALID_TOKEN(401, "1003", "유효하지 않은 토큰입니다."),

  ;

  private final int status;
  private final String code;
  private final String message;

  ExceptionEnum(final int status, final String code, final String message)
  {
    this.status = status;
    this.code = code;
    this.message = message;
  }
}
