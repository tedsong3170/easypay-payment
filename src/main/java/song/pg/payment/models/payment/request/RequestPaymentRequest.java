package song.pg.payment.models.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import song.pg.payment.models.payment.PaymentInfoEntity;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RequestPaymentRequest implements Serializable
{
  private static final long serialVersionUID = 5541325694662477660L;

  private String paymentId;
  private String mid;
  private BigDecimal amount;
  private BigDecimal taxFreeAmount;
  private String orderId;
  private String orderName;
  private String paymentMethodId;
  private Integer installmentMonth;
  private String callbackUrl;

  public boolean validate(PaymentInfoEntity entity)
  {
    return this.mid.equals(entity.getMid()) &&
            this.amount.equals(entity.getTotalAmount()) &&
            this.orderId.equals(entity.getOrderId()) &&
            this.orderName.equals(entity.getOrderName());
  }
}
