package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Room;
import io.swagger.models.auth.In;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepo extends CrudRepository<Room, Integer> {
}
