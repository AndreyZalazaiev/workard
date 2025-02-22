package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Recommendation;
import andrew.projects.workard.Domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RecommendationRepo extends JpaRepository<Recommendation,Integer> {
    @Query(value = " select v from Company c, Room r, Visit v " +
            "where c.id=r.idCompany and r.id=v.idRoom and c.id=:idCompany " +
            "and v.entryTime >:period")
    ArrayList<Visit> getVisitsForThePeriod(@Param("idCompany") int idCompany,@Param("period") LocalDateTime dateOfStart);

    ArrayList<Recommendation> getAllByDateIsAfterAndIdCompany(LocalDateTime date, int idCompany);
}
