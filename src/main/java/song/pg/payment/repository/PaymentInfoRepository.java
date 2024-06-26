package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.payment.models.payment.PaymentInfoEntity;

import java.util.Optional;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfoEntity, String>
{
  Optional<PaymentInfoEntity> findByDiAndPaymentId(String di, String paymentId);
  Long countByStatusIsNotAndOrderIdAndMid(String status, String orderId, String mid);
}
