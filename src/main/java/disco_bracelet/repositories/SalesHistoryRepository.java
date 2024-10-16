package disco_bracelet.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.SalesHistoryEntity;
import disco_bracelet.enteties.WaiterEntity;

public interface SalesHistoryRepository extends CrudRepository<SalesHistoryEntity, Integer> {

	List<SalesHistoryEntity> findBySoldAtBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<SalesHistoryEntity> findByWaiterAndSoldAtBetween(WaiterEntity waiter, LocalDateTime startDate,
			LocalDateTime endDate);

}
