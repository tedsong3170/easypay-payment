package song.pg.payment.models.payment.approval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePaymentApproval implements Serializable
{

  private static final long serialVersionUID = 3271193566389819916L;
  private String code;
  private String message;
  private String data;
}
