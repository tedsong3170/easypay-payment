package song.pg.payment.models.payment.ready;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RequestPaymentReady implements Serializable
{
  private static final long serialVersionUID = 3430522701524337330L;

  private String mid;
  private BigDecimal amount;
  private BigDecimal taxFreeAmount;
  private String orderId;
  private String orderName;
}
