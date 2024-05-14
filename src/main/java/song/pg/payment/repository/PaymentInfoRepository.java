package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.payment.models.payment.ready.PaymentInfoEntity;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, String>
{
  PaymentInfoEntity findByOrderIdAndAndMid(String orderId, String mid);
  Long countByStatusIsNotAndOrderIdAndMid(String status, String orderId, String mid);
}
