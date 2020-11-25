package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Room;
import andrew.projects.workard.Domain.User;
import io.swagger.models.auth.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepo extends CrudRepository<Room, Integer> {
    List<Room> getAllByIdCompany(@Param("idCompany") int idCompany);


}
