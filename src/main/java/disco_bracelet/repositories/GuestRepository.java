package disco_bracelet.repositories;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.GuestEntity;

public interface GuestRepository extends CrudRepository<GuestEntity, Integer> {

}
