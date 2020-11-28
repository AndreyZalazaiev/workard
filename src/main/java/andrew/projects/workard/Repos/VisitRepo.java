package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.DTO.HotSpotsDTO;
import andrew.projects.workard.Domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface VisitRepo extends CrudRepository<Visit, Integer> {
    ArrayList<Visit> findAllByIdEmployeeAndIdRoomOrderByEntryTimeDesc(@Param("idEmployee") int idEmployee, @Param("idRoom") int idRoom);

    @Query("SELECT new andrew.projects.workard.Domain.DTO.HotSpotsDTO(v.idRoom, count(v.entryTime)) FROM Visit v,Company c, Room r " +
            "where  c.id=r.idCompany and v.idRoom=r.id and c.id=:idCompany and v.exitTime is null " +
            "group by v.idRoom")
    List<HotSpotsDTO> findHotPoints(@Param("idCompany") int idCompany);
}
