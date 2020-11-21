package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepo extends CrudRepository<Recommendation,Integer> {
}
