package andrew.projects.workard.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Device {
    @Id
    String deviceCode;
    @Column(nullable = false)
    Integer idRoom;
}
