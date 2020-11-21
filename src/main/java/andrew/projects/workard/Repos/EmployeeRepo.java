package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee,Integer> {
}
