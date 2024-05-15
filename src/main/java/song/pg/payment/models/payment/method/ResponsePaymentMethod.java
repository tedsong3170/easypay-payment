package song.pg.payment.models.payment.method;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponsePaymentMethod implements Serializable
{
  private static final long serialVersionUID = -5436426056768189573L;

  private String id;
  private PaymentMethodType method;
  private String nickName;
}

