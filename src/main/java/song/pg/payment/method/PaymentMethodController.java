package song.pg.payment.method;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import song.pg.payment.models.common.CommonResponse;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;
import song.pg.payment.models.payment.method.card.RequestPaymentMethodCardRegister;
import song.pg.payment.utils.KnownException;

import static song.pg.payment.utils.ExceptionEnum.*;

@RestController
@RequestMapping("/api/payment/method")
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodController {

  private final PaymentMethodService paymentMethodService;
  @PostMapping("/card/v1")
  public ResponseEntity<CommonResponse<ResponsePaymentMethod>> registerCardInfo(
    @RequestBody @Valid RequestPaymentMethodCardRegister requestPaymentMethodCardRegister
  )
  {
    log.info("카드 등록 시작");

    try
    {
      UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return ResponseEntity.ok()
        .body(paymentMethodService.registerCardInfo(
          principal.getUsername(),
          principal.getPassword(),
          requestPaymentMethodCardRegister
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
        .body(new CommonResponse<>(UNKNOWN_ERROR.getCode(), UNKNOWN_ERROR.getMessage(), null));
    }

  }
}
