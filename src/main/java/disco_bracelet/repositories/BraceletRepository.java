package disco_bracelet.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import disco_bracelet.enteties.BraceletEntity;


public interface BraceletRepository extends CrudRepository<BraceletEntity, Integer> {
	


}
