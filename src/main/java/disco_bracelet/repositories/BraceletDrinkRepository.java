package disco_bracelet.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.BraceletDrinkEntity;
import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;

public interface BraceletDrinkRepository extends CrudRepository<BraceletDrinkEntity, Integer> {

	public BraceletDrinkEntity findByBraceletAndDrink(BraceletEntity bracelet, DrinkEntity drink);
	List<BraceletDrinkEntity> findByBracelet(BraceletEntity bracelet);
}
