package song.pg.payment.api.payment;

import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;

public interface PaymentService
{
  CommonResponse<ResponsePaymentReady> readyPayment(
    final String ci,
    final RequestPaymentReady requestPaymentReady
  );
}
