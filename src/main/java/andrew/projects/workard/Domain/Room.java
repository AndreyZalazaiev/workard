package andrew.projects.workard.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Room extends BaseEntity {
    @Column(nullable = false)
    private Integer idCompany;
    private String name;
    private Integer recommendedValue;
    private String extra;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idRoom", referencedColumnName = "id")
    private List<Visit> visits = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idRoom", referencedColumnName = "id")
    private List<Device> devices = new ArrayList<>();

    public void addVisit(Visit v) {
        visits.add(v);
    }

    public void addDevice(Device d) {
        devices.add(d);
    }
}
