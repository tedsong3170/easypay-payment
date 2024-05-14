package song.pg.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.pg.payment.models.customer.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String>
{
//  Optional<CustomerEntity> findByCiAndMid(final String ci, final String mid);
  CustomerEntity findByCiAndMid(final String ci, final String mid);
}
