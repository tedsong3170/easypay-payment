package song.pg.payment.api.method;

import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.method.card.RequestPaymentMethodCardRegister;

public interface PaymentMethodService
{
  CommonResponse<ResponsePaymentMethod> registerCardInfo(
    final String di,
    final String mid,
    final RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  );
}
