package song.pg.payment.models.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVo implements Serializable
{
  private static final long serialVersionUID = -4167816158804514542L;

  // 사용자DI
  private String di;

  // 가맹점ID
  private String mid;

  // 사용자CI
  private String ci;

  // 이메일주소
  private String email;

  // 등록일
  private LocalDateTime createAt;

  // 수정일
  private LocalDateTime updateAt;

  public CustomerVo(CustomerEntity entity)
  {
    this.di = entity.getDi();
    this.mid = entity.getMid();
    this.ci = entity.getCi();
    this.email = entity.getEmail();
    this.createAt = entity.getCreateAt();
    this.updateAt = entity.getUpdateAt();
  }
}
