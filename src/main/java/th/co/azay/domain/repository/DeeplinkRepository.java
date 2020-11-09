package th.co.azay.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.co.azay.domain.entity.Deeplink;
import th.co.azay.domain.entity.DeeplinkPK;

@Repository
public interface DeeplinkRepository extends JpaRepository<Deeplink, DeeplinkPK> {

}
