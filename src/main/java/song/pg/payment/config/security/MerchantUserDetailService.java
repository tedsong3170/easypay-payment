package song.pg.payment.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import song.pg.payment.models.merchant.MerchantInfoEntity;
import song.pg.payment.models.merchant.MerchantInfoVo;
import song.pg.payment.repository.MerchantInfoRepository;

import java.util.Optional;

@Component
@Slf4j
public class MerchantUserDetailService implements UserDetailsService {
  private final MerchantInfoRepository merchantInfoRepository;

  public MerchantUserDetailService(MerchantInfoRepository merchantInfoRepository) {
    this.merchantInfoRepository = merchantInfoRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<MerchantInfoEntity> entity = merchantInfoRepository.findById(username);

    if (entity.isEmpty()) {
      log.error("[가맹점인증] 가맹점 정보를 찾을 수 없습니다. mid: {}", username);
      throw new UsernameNotFoundException("User not found");
    }

    return new MerchantUserDetails(new MerchantInfoVo(entity.get()));
  }
}
