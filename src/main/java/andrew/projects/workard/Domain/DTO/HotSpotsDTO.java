package andrew.projects.workard.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotSpotsDTO {
    Integer idRoom;
    Integer recommendedValue;
    Long employeesNow;
}
