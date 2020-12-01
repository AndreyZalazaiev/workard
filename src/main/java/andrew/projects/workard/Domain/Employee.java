package andrew.projects.workard.Domain;

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
public class Employee extends BaseEntity {
    private String name;
    private String occupation;
    @Column(nullable = false)
    private String RFIDtag;
    @Column(nullable = false)
    private Integer idCompany;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idEmployee", referencedColumnName = "id")
    private List<Visit> visits = new ArrayList<>();


    public void addVisit(Visit v) {
        visits.add(v);
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idEmployee", referencedColumnName = "id")
    private List<Recommendation> recommendations = new ArrayList<>();
}
