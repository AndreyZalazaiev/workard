package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepo extends CrudRepository<Company,Integer> {
}
