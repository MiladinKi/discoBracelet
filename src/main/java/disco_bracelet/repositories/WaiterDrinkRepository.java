package disco_bracelet.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;
import disco_bracelet.enteties.dtoes.WaiterDrinkReportDTO;

public interface WaiterDrinkRepository extends CrudRepository<WaiterDrinkEntity, Integer> {
	public WaiterDrinkEntity findByWaiterAndDrink(WaiterEntity waiter, DrinkEntity drink);

	public List<WaiterDrinkEntity> findByWaiter(WaiterEntity waiter);


	@Query("SELECT wd FROM WaiterDrinkEntity wd WHERE wd.createdAt BETWEEN :startDate AND :endDate")
    List<WaiterDrinkEntity> findDrinksByDateRange(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

//    @Query("SELECT wd FROM WaiterDrinkEntity wd WHERE wd.waiter.id = :waiterId AND wd.createdAt BETWEEN :startDate AND :endDate")
//    List<WaiterDrinkEntity> findDrinksByWaiterAndDateRange(@Param("waiterId") Integer waiterId,
//                                                           @Param("startDate") LocalDateTime startDate,
//                                                           @Param("endDate") LocalDateTime endDate);

	List<WaiterDrinkEntity> findByWaiterIdAndCreatedAtBetween(Integer waiterId, LocalDateTime startDate, LocalDateTime endDate);

}
