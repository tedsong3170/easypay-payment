package song.pg.payment.utils;

import lombok.Getter;

@Getter
public enum ExceptionEnum
{
  UNKNOWN_PAYMENT_METHOD(500, "SPM0001", "알 수 없는 결제 수단입니다."),
  ALREADY_EXIST_NICKNAME(400, "CPM0001", "이미 등록된 별칭입니다."),
  INVALID_CARD_NUMBER(400, "CPM0002", "유효하지 않은 카드 번호입니다."),

  INVALID_TOKEN(401, "CAU0001", "유효하지 않은 토큰입니다."),

  ENCRYPT_ERROR(400, "CCM0001", "암호화 중 오류 발생하였습니다."),
  DECRYPT_ERROR(400, "CCM0002", "복호화 중 오류 발생하였습니다."),
  INVALID_JSON_FORMAT(400, "CCM0003", "유효하지 않은 JSON 형식입니다."),
  INVALID_TO_JSON_FORMAT(400, "CCM0004", "유효하지 않은 JSON 직렬화 형식입니다."),

  ALREADY_EXIST_PAYMENT(400, "CPS0001", "이미 결제가 진행중인 주문입니다."),
  NOT_EXIST_PAYMENT(400, "CPS0002", "존재하지 않는 결제입니다."),
  INVALID_PAYMENT_REQUEST(400, "CPS0003", "유효하지 않은 결제 요청입니다."),
  NOT_EXIST_PAYMENT_LEDGER(400, "CPS0004", "존재하지 않는 결제원장입니다."),

  UNKNOWN_ERROR(500, "CCM9999", "알 수 없는 오류가 발생하였습니다."),
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
