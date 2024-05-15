package song.pg.payment.method;

import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.method.card.RequestPaymentMethodCardRegister;

public interface PaymentMethodService
{
  /**
   * 카드 정보 등록
   * @param di
   * @param mid
   * @param requestPaymentMethodCardRegister
   * @return
   */
  CommonResponse<ResponsePaymentMethod> registerCardInfo(
    final String di,
    final String mid,
    final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  );
}
