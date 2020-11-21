package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepo extends CrudRepository<Visit,Integer> {
}
