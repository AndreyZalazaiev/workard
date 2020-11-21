package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepo extends CrudRepository<Device,Integer> {
}
