package song.pg.payment.models.payment.approval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import song.pg.payment.models.payment.request.RequestPaymentRequest;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RequestPaymentApproval implements Serializable
{
  private static final long serialVersionUID = -671106201416150628L;

  private String paymentId;
  private String paymentLedgerId;
  private String mid;
  private BigDecimal amount;
  private BigDecimal taxFreeAmount;
  private String orderId;
  private String orderName;
  private Integer installmentMonth;

  private String paymentToken;
  private String approvalUrl;

  public RequestPaymentApproval(
    final RequestPaymentRequest request,
    final String paymentLedgerId,
    final String paymentToken,
    final String approvalUrl
  )
  {
    this.paymentId = request.getPaymentId();
    this.mid = request.getMid();
    this.amount = request.getAmount();
    this.taxFreeAmount = request.getTaxFreeAmount();
    this.orderId = request.getOrderId();
    this.orderName = request.getOrderName();
    this.installmentMonth = request.getInstallmentMonth();
    this.paymentToken = paymentToken;
    this.paymentLedgerId = paymentLedgerId;
    this.approvalUrl = approvalUrl;
  }
}
