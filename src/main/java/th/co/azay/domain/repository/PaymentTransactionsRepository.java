package th.co.azay.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.azay.domain.entity.PaymentTrans;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTrans, String> {

}
