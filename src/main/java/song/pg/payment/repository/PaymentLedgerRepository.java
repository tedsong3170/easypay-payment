package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import song.pg.payment.models.payment.ledger.PaymentLedgerEntity;

public interface PaymentLedgerRepository extends JpaRepository<PaymentLedgerEntity, String>
{
}
