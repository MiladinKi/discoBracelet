package disco_bracelet.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import disco_bracelet.enteties.WaiterEntity;

@Repository
public interface WaiterRepository extends CrudRepository<WaiterEntity, Integer> {

}
