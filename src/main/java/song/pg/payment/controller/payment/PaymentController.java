package song.pg.payment.controller.payment;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import song.pg.payment.api.payment.PaymentService;
import song.pg.payment.config.security.MerchantUserDetails;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController
{
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService)
  {
    this.paymentService = paymentService;
  }

  @PostMapping("/ready/v1")
  public ResponseEntity<CommonResponse<ResponsePaymentReady>> readyPayment(
    @RequestHeader("X-CUSTOMER-ID") String ci,
    @RequestBody @Valid RequestPaymentReady requestPaymentReady
  )
  {
    log.debug("결제 준비 요청");
    log.debug("결제 준비 요청 사용자: {}", ci);
    log.debug("결제 준비 요청 바디: {}", requestPaymentReady.toString());

    MerchantUserDetails user = (MerchantUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    log.debug("결제 준비 요청 가맹점ID: {}", user.getUsername());
    log.debug("결제 준비 요청 가맹점시크릿키: {}", user.getPassword());

    return ResponseEntity.ok()
      .body(paymentService.readyPayment(ci, requestPaymentReady)
      );
  }
}
