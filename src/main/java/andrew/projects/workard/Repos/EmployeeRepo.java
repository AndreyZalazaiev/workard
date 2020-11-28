package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepo extends CrudRepository<Employee,Integer> {
    public Optional<Employee> findEmployeeByRFIDtag(@Param("RFIDtag") String RFIDtag);
}
