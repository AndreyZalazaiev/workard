package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.DTO.HotSpotsDTO;
import andrew.projects.workard.Domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit, Integer> {
    ArrayList<Visit> findAllByIdEmployeeAndIdRoomOrderByEntryTimeDesc(@Param("idEmployee") int idEmployee, @Param("idRoom") int idRoom);

    @Query("SELECT new andrew.projects.workard.Domain.DTO.HotSpotsDTO(v.idRoom,r.recommendedValue, count(v.id)) FROM Visit v,Company c, Room r " +
            "where v.exitTime is null and v.idRoom=r.id and c.id=r.idCompany and c.id=:idCompany  " +
            "group by v.idRoom")
    List<HotSpotsDTO> findHotPoints(@Param("idCompany") int idCompany);

    List<Visit> findAllByIdRoom(@Param("idRoom") int idRoom);

}
