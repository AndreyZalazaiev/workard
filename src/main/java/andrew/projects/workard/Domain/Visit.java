package andrew.projects.workard.Domain;

import andrew.projects.workard.Service.CustomDateInternatsionalizator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Visit extends BaseEntity {
    @Column(nullable = false)
    private Integer idRoom;
    @Column(nullable = false)
    private Integer idEmployee;
    @Column(nullable = false)
    @JsonSerialize(using  = CustomDateInternatsionalizator.class)
    private LocalDateTime entryTime;
    @JsonSerialize(using  = CustomDateInternatsionalizator.class)
    private LocalDateTime exitTime;
}
