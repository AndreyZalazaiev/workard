package andrew.projects.workard.Repos;


import andrew.projects.workard.Domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role,Integer> {
}
