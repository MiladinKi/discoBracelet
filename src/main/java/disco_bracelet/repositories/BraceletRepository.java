package disco_bracelet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import disco_bracelet.enteties.BraceletEntity;


public interface BraceletRepository extends CrudRepository<BraceletEntity, Integer> {
	List<BraceletEntity> findByGuestIsNull();
	long countByGuestIsNull();
	Optional<BraceletEntity>findByBarCode(String barCode);
//	 @Query("SELECT b FROM BraceletEntity b WHERE EXISTS (SELECT 1 FROM BraceletDrinkEntity bd WHERE bd.bracelet = b)")
//	List<BraceletEntity> findBraceletsWithDrinks();

}
