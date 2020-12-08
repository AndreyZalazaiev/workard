package andrew.projects.workard.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String emailConfirmation;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

    private List<Role> authorities = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    private List<Company> companies = new ArrayList<>();

    public void addAuthority(Role r) {
        this.authorities.add(r);
    }

    public void addCompany(Company c) {
        companies.add(c);
    }


}