package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Device;
import andrew.projects.workard.Domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device,String> {
    ArrayList<Device> findByIdRoom(@Param("idRoom") int idRoom);
}
