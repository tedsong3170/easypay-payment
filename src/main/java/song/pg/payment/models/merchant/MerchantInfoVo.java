package song.pg.payment.models.merchant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 가맹점_정보 가맹점_정보
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantInfoVo {

  // 가맹점ID 가맹점ID
  @Id
  private String mid;

  // 가맹점명 가맹점명
  private String name;

  // 대표자명 대표자명
  private String ceoName;

  // 업태 업태
  private String bizType;

  // 업종 업종
  private String bizItem;

  // 사업자구분. 개인, 법인 사업자구분. 개인, 법인
  private String bizOwnerType;

  // 사업자번호 사업자번호
  private String bizNumber;

  // 대표자연락처 대표자연락처
  private String ceoPhoneNumber;

  // 시크릿키 시크릿키
  private String secretKey;

  // 등록일 등록일
  private LocalDateTime createAt;

  // 등록자 등록자
  private String createBy;

  // 수정일 수정일
  private LocalDateTime updateAt;

  // 수정자 수정자
  private String updateBy;

  public MerchantInfoVo(MerchantInfoEntity entity) {
    this.mid = entity.getMid();
    this.name = entity.getName();
    this.ceoName = entity.getCeoName();
    this.bizType = entity.getBizType();
    this.bizItem = entity.getBizItem();
    this.bizOwnerType = entity.getBizOwnerType();
    this.bizNumber = entity.getBizNumber();
    this.ceoPhoneNumber = entity.getCeoPhoneNumber();
    this.secretKey = entity.getSecretKey();
    this.createAt = entity.getCreateAt();
    this.createBy = entity.getCreateBy();
    this.updateAt = entity.getUpdateAt();
    this.updateBy = entity.getUpdateBy();
  }
}
