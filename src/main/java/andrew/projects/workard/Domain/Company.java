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
public class Company extends BaseEntity {
    @Column(nullable = false)
    private Integer idUser;
    @Column(nullable = false)
    private String Name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private List<Recommendation> recommendations = new ArrayList<>();


}