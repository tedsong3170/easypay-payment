package song.pg.payment.models.payment.ledger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.pg.payment.models.payment.PaymentInfoEntity;
import song.pg.payment.utils.UUIDGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_ledger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentLedgerEntity
{
  // 결제원장ID
  @Id
  @Column(name="ledger_id", nullable = false, columnDefinition = "char")
  private String ledgerId = UUIDGenerator.generateUUID();

  // 결제ID
  @Column(name="payment_id", nullable = false, columnDefinition = "char")
  private String paymentId;

  // 결제수단
  @Column(name="method", nullable = false)
  private String method;

  // 결제수단ID
  @Column(name="method_id", nullable = false, columnDefinition = "char")
  private String methodId;

  // 결제토큰
  @Column(name="token", nullable = false)
  private String token;

  // 결제금액
  @Column(name="amount", nullable = false)
  private BigDecimal amount;

  // 결제상태. 요청, 승인, 거부 등
  @Column(name="status", nullable = false)
  private String status;

  // 결제승인코드
  @Column(name="approval_code", columnDefinition = "char")
  private String approvalCode;

  // 에러코드
  @Column(name="error_code")
  private String errorCode;

  // 에러메세지
  @Column(name="error_message")
  private String errorMessage;

  // 등록일
  @Column(name="create_at", nullable = false)
  private LocalDateTime createAt = LocalDateTime.now();

  public PaymentLedgerEntity(final PaymentInfoEntity info,
                             final String method,
                             final String methodId,
                             final String token
  )
  {
    this.paymentId = info.getPaymentId();
    this.method = method;
    this.methodId = methodId;
    this.amount = info.getTotalAmount();
    this.status = info.getStatus();
    this.token = token;
  }
}
