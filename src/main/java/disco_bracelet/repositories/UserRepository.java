package disco_bracelet.repositories;

import org.springframework.data.repository.CrudRepository;

import disco_bracelet.enteties.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
