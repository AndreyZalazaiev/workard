package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface RoomRepo extends CrudRepository<Room, Integer> {
    ArrayList<Room> getAllByIdCompany(@Param("idCompany") int idCompany);


}
