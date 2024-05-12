package song.pg.payment.models.payment.method.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestPaymentMethodCardRegister {
  @NotBlank(message = "카드번호는 필수값입니다.")
  private String cardNumber;

  @NotNull(message = "만료년도는 필수값입니다.")
  private int expireYear;

  @NotNull(message = "만료월은 필수값입니다.")
  private int expireMonth;

  @NotNull(message = "CVC는 필수값입니다.")
  private int cvc;

  @NotNull(message = "비밀번호는 필수값입니다.")
  private int password;

  @NotBlank(message = "카드소유자명은 필수값입니다.")
  private String cardHolderName;
}
