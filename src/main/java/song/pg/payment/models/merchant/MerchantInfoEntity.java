package song.pg.payment.models.merchant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

// 가맹점_정보 가맹점_정보
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "merchant_info")
public class MerchantInfoEntity {

  // 가맹점ID 가맹점ID
  @Id
  @Column(name = "mid", columnDefinition = "char")
  private String mid;

  // 가맹점명 가맹점명
  @Column(name = "name")
  private String name;

  // 대표자명 대표자명
  @Column(name = "ceo_name")
  private String ceoName;

  // 업태 업태
  @Column(name = "biz_type")
  private String bizType;

  // 업종 업종
  @Column(name = "biz_item")
  private String bizItem;

  // 사업자구분. 개인, 법인 사업자구분. 개인, 법인
  @Column(name = "biz_owner_type")
  private String bizOwnerType;

  // 사업자번호 사업자번호
  @Column(name = "biz_number")
  private String bizNumber;

  // 대표자연락처 대표자연락처
  @Column(name = "ceo_phone_number")
  private String ceoPhoneNumber;

  // 시크릿키 시크릿키
  @Column(name = "secret_key", columnDefinition = "char")
  private String secretKey;

  // 등록일 등록일
  @Column(name = "create_at")
  private LocalDateTime createAt;

  // 등록자 등록자
  @Column(name = "create_by")
  private String createBy;

  // 수정일 수정일
  @Column(name = "update_at")
  private LocalDateTime updateAt;

  // 수정자 수정자
  @Column(name = "update_by")
  private String updateBy;

}
