package song.pg.payment.api.method;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import song.pg.payment.models.payment.method.card.RequestPaymentMethodCardRegister;

@RestController
@RequestMapping("/api/payment/method")
@Slf4j
public class PaymentMethodController {

  @PostMapping("/card/v1")
  public String registerCardInfo(
    @RequestHeader("Authorization") String authorization,
    @RequestBody @Valid RequestPaymentMethodCardRegister requestPaymentMethodCardRegister,
    BindingResult bindingResult
  )
  {
    if (bindingResult.hasErrors()) {
      log.error("Validation error: {}", bindingResult.getAllErrors());
      return "Validation error";
    }
    log.info("Authorization: {}", authorization);
    log.info("registerCardInfoVo: {}", requestPaymentMethodCardRegister.toString());

    return "Card info registered";
  }
}
