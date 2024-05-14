package song.pg.payment.models.customer;

import com.google.common.hash.Hashing;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_customer_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerEntity
{
  // 사용자DI
  @Id
  @Column(name="di", nullable = false, columnDefinition = "char")
  private String di;

  // 가맹점ID
  @Column(name="mid", nullable = false, columnDefinition = "char")
  private String mid;

  // 사용자CI
  @Column(name="ci", nullable = false, columnDefinition = "char")
  private String ci;

  // 이메일주소
  @Column(name="email")
  private String email;

  // 등록일
  @Column(name="create_at", nullable = false)
  private LocalDateTime createAt = LocalDateTime.now();

  // 수정일
  @Column(name="update_at", nullable = false)
  private LocalDateTime updateAt = LocalDateTime.now();

  /**
   * 신규 등록시 사용
   * @param ci
   * @param mid
   */
  public CustomerEntity(final String ci, final String mid)
  {
    this.ci = ci;
    this.mid = mid;
    this.di = Hashing.sha256().hashString(ci + mid, StandardCharsets.UTF_8).toString();
  }
}
