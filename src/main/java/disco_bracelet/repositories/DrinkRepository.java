package disco_bracelet.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.BraceletEntity;
import disco_bracelet.enteties.DrinkEntity;

public interface DrinkRepository extends CrudRepository<DrinkEntity, Integer> {
	public List<DrinkEntity> findByBracelet (BraceletEntity bracelet);
}
