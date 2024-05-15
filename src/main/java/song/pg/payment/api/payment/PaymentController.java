package song.pg.payment.api.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import song.pg.payment.config.security.CustomerUserDetails;
import song.pg.payment.config.security.MerchantUserDetails;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;
import song.pg.payment.models.payment.request.RequestPaymentRequest;
import song.pg.payment.models.payment.request.ResponsePaymentRequest;

@RestController
@RequestMapping("/api/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController
{
  private final PaymentService paymentService;

  @PostMapping("/ready/v1")
  public ResponseEntity<CommonResponse<ResponsePaymentReady>> readyPayment(
    @RequestHeader("X-CUSTOMER-ID") String ci,
    @RequestBody @Valid final RequestPaymentReady requestPaymentReady
  )
  {
    log.info("결제 준비 요청");

    MerchantUserDetails user = (MerchantUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return ResponseEntity.ok()
      .body(paymentService.readyPayment(ci, requestPaymentReady)
      );
  }

  @PostMapping("/request/easy/v1")
  public ResponseEntity<CommonResponse<ResponsePaymentRequest>> requestPayment(
    @RequestBody @Valid final RequestPaymentRequest requestPaymentRequest
  )
  {
    log.info("결제 요청");

    CustomerUserDetails user = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return ResponseEntity.ok()
      .body(paymentService.requestPayment(
          user.getUsername(),
          requestPaymentRequest
        )
      );
  }
}
