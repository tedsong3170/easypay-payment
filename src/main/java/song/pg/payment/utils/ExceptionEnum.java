package song.pg.payment.utils;

import lombok.Getter;

@Getter
public enum ExceptionEnum
{
  ALREADY_EXIST_PAYMENT(400, "1001", "이미 결제가 진행중인 주문입니다."),
  UNKNOWN_PAYMENT_METHOD(500, "1002", "알 수 없는 결제 수단입니다."),
  INVALID_TOKEN(401, "1003", "유효하지 않은 토큰입니다."),
  ENCRYPT_ERROR(500, "1004", "암호화 중 오류 발생하였습니다."),
  DECRYPT_ERROR(500, "1005", "복호화 중 오류 발생하였습니다."),

  ALREADY_EXIST_NICKNAME(400, "1006", "이미 등록된 별칭입니다."),
  INVALID_CARD_NUMBER(400, "1007", "유효하지 않은 카드 번호입니다."),
  INVALID_JSON_FORMAT(400, "1008", "유효하지 않은 JSON 형식입니다."),
  INVALID_TO_JSON_FORMAT(400, "1009", "유효하지 않은 JSON 직렬화 형식입니다."),

  NOT_EXIST_PAYMENT(400, "1010", "존재하지 않는 결제입니다."),

  INVALID_PAYMENT_REQUEST(400, "1011", "유효하지 않은 결제 요청입니다."),

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
