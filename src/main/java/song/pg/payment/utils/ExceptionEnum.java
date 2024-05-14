package song.pg.payment.utils;

import lombok.Getter;

@Getter
public enum ExceptionEnum
{
  ALREADY_EXIST_PAYMENT("1001", "이미 결제가 진행중인 주문입니다."),

  ;

  private final String code;
  private String message;

  ExceptionEnum(String code, String message)
  {
    this.code = code;
    this.message = message;
  }
}
