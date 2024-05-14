package song.pg.payment.models.payment.ready;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import song.pg.payment.utils.UUIDGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInfoEntity
{
  // 결제ID
  @Id
  @Column(name="payment_id", nullable = false, columnDefinition = "char")
  private String paymentId = UUIDGenerator.generateUUID();

  // 사용자DI
  @Column(name="di", nullable = false, columnDefinition = "char")
  private String di;

  // 가맹점ID
  @Column(name="mid", nullable = false)
  private String mid;

  // 결제타입 간편,정기 등
  @Column(name="type")
  private String type;

  // 주문ID
  @Column(name="order_id", nullable = false)
  private String orderId;

  // 주문명
  @Column(name="order_name", nullable = false)
  private String orderName;

  // 총결제금액
  @Column(name="total_amount", nullable = false)
  private BigDecimal totalAmount;

  // 결제잔액
  @Column(name="balance", nullable = false)
  private BigDecimal balance = BigDecimal.ZERO;

  // 결제상태 요청, 승인, 거절 등
  @Column(name="status", nullable = false)
  private String status;

  // 할부개월수 카드일때만
  @Column(name="installment_month")
  private Integer installmentMonth;

  // 결제요청일자 최초결제요청일시
  @Column(name="requested_at")
  private LocalDateTime requestedAt;

  // 결제승인일자 최종결제승인일시
  @Column(name="approved_at")
  private LocalDateTime approvedAt;

  // 에러코드
  @Column(name="error_code", columnDefinition = "char")
  private String errorCode;

  // 에러메세지
  @Column(name="error_message")
  private String errorMessage;

  // 등록일
  @Column(name="create_at", nullable = false)
  private LocalDateTime createAt = LocalDateTime.now();

  // 수정일
  @Column(name="update_at", nullable = false)
  private LocalDateTime updateAt = LocalDateTime.now();

  /**
   * 결제 준비 요청시 사용
   */
  public PaymentInfoEntity(final String di,
                           final RequestPaymentReady ready
  )
  {
    this.di = di;
    this.mid = ready.getMid();
    this.orderId = ready.getOrderId();
    this.orderName = ready.getOrderName();
    this.totalAmount = ready.getAmount();
    this.status = "READY";
  }
}
