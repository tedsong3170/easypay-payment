package song.pg.payment.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import song.pg.payment.config.security.CustomerUserDetails;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.ready.RequestPaymentReady;
import song.pg.payment.models.payment.ready.ResponsePaymentReady;
import song.pg.payment.models.payment.request.RequestPaymentRequest;
import song.pg.payment.models.payment.request.ResponsePaymentRequest;
import song.pg.payment.utils.ExceptionEnum;
import song.pg.payment.utils.KnownException;

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

    try
    {
      return ResponseEntity.ok()
        .body(paymentService.readyPayment(ci, requestPaymentReady)
        );
    }
    catch (KnownException e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(e.getCode(), e.getMessage(), null));
    }
    catch (Exception e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(ExceptionEnum.UNKNOWN_ERROR.getCode(), ExceptionEnum.UNKNOWN_ERROR.getMessage(), null));
    }
  }

  @PostMapping("/request/easy/v1")
  public ResponseEntity<CommonResponse<ResponsePaymentRequest>> requestPayment(
    @RequestBody @Valid final RequestPaymentRequest requestPaymentRequest
  )
  {
    log.info("결제 요청");

    try
    {
      CustomerUserDetails user = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return ResponseEntity.ok()
        .body(paymentService.requestPayment(
            user.getUsername(),
            requestPaymentRequest
          )
        );
    }
    catch (KnownException e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(e.getCode(), e.getMessage(), null));
    }
    catch (Exception e)
    {
      return ResponseEntity.badRequest()
        .body(new CommonResponse<>(ExceptionEnum.UNKNOWN_ERROR.getCode(), ExceptionEnum.UNKNOWN_ERROR.getMessage(), null));
    }
  }
}
