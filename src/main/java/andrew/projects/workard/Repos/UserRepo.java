package andrew.projects.workard.Repos;


import andrew.projects.workard.Domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);

	@Query("from User u where u.emailConfirmation=:emailConfirmation")
	Optional<User> findUserByActivationCode(@Param("emailConfirmation") String emailConfirmation);
	
}