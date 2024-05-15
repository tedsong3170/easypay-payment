package song.pg.payment.api.payment;

import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;
import song.pg.payment.models.payment.request.RequestPaymentRequest;
import song.pg.payment.models.payment.request.ResponsePaymentRequest;

public interface PaymentService
{
  CommonResponse<ResponsePaymentReady> readyPayment(
    final String ci,
    final RequestPaymentReady requestPaymentReady
  );

  CommonResponse<ResponsePaymentRequest> requestPayment(
    final String di,
    final RequestPaymentRequest requestPaymentReady
  );

  void approvePaymentRequest(
    final String Request
  );
}
