package song.pg.payment.models.payment.ready;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import song.pg.payment.models.payment.method.ResponsePaymentMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponsePaymentReady implements Serializable
{
  private static final long serialVersionUID = 4107148515125443539L;
  private String paymentId;
  private String customerDi;
  private String accessToken;
  private List<ResponsePaymentMethod> paymentMethod;

  public ResponsePaymentReady(
    final String paymentId,
    final String customerDi,
    final String accessToken
  )
  {
    this.paymentId = paymentId;
    this.customerDi = customerDi;
    this.accessToken = accessToken;
    this.paymentMethod = new ArrayList<>();
  }
}
