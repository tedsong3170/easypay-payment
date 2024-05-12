package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.payment.models.merchant.MerchantInfoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantInfoRepository extends JpaRepository<MerchantInfoEntity, String>
{
  public Optional<MerchantInfoEntity> findByMidAndSecretKey(String mid, String secretKey);
}
