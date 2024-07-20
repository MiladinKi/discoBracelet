package disco_bracelet.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.DrinkEntity;
import disco_bracelet.enteties.WaiterDrinkEntity;
import disco_bracelet.enteties.WaiterEntity;

public interface WaiterDrinkRepository extends CrudRepository<WaiterDrinkEntity, Integer> {
	public WaiterDrinkEntity findByWaiterAndDrink(WaiterEntity waiter, DrinkEntity drink);
	public List<WaiterDrinkEntity> findByWaiter(WaiterEntity waiter);
}
