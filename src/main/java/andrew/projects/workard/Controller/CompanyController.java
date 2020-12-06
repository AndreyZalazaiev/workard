package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Company;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.CompanyRepo;
import andrew.projects.workard.Repos.UserRepo;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public ResponseEntity<?> getCompanies(HttpServletRequest req) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        return ResponseEntity.ok(companyRepo.getAllByIdUser(current.getId()));
    }

    @PostMapping
    public ResponseEntity<?> postCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        company.setIdUser(current.getId());
        if (current.getCompanies().stream().filter(c -> c.getId() == company.getId()).count() > 0) {
            Company stored = companyRepo.findById(company.getId()).get();
            stored.setName(company.getName());
            return ResponseEntity.ok(companyRepo.save(stored));
        }
        return ResponseEntity.ok(companyRepo.save(company));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (current.getCompanies().stream().filter(c -> c.getId() == company.getId()).count() > 0) {
            companyRepo.deleteInBatch(Arrays.asList(company));

            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
}
