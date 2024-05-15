package song.pg.payment.models.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponsePaymentRequest implements Serializable
{
  private static final long serialVersionUID = -5945807802532750085L;

  private String paymentId;
  private String customerDi;
  private String paymentMethodId;
  private String callbackUrl;
}
