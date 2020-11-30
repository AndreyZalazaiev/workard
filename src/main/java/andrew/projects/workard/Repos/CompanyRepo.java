package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompanyRepo extends CrudRepository<Company,Integer> {
    public Iterable<Company> getAllByIdUser(@Param("IdUser") int IdUser);
}
