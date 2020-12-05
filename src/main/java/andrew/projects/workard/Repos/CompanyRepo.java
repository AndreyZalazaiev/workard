package andrew.projects.workard.Repos;

import andrew.projects.workard.Domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Iterable<Company> getAllByIdUser(@Param("IdUser") int IdUser);

    @Override
    @Transactional
    void deleteById(Integer integer);

}
