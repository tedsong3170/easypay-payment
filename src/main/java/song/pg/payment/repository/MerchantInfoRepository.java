package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.payment.models.merchant.MerchantInfoEntity;

@Repository
public interface MerchantInfoRepository extends JpaRepository<MerchantInfoEntity, String>
{
}
