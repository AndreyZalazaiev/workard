package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Company;
import andrew.projects.workard.Domain.Employee;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.CompanyRepo;
import andrew.projects.workard.Repos.EmployeeRepo;
import andrew.projects.workard.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    CompanyRepo companyRepo;

    @PostMapping
    public ResponseEntity<?> createEmployee(HttpServletRequest req, @RequestBody Employee e) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        Optional<Company> company = companyRepo.findById(e.getIdCompany());
        if (company.isPresent()) {
            if (company.get().getIdUser() == currentUser.getId()) {
                if (e.getId() != null) {
                    Employee stroed = employeeRepo.findById(e.getId()).get();
                    stroed.setName(e.getName());
                    stroed.setOccupation(e.getOccupation());
                    stroed.setRFIDtag(e.getRFIDtag());
                    return ResponseEntity.ok(employeeRepo.save(stroed));
                }
                return ResponseEntity.ok(employeeRepo.save(e));
            }
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmployee(HttpServletRequest req, @RequestBody Employee e) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (RoomController.isOwnerOfRoom(e.getIdCompany(), currentUser)) {
            employeeRepo.deleteInBatch(Arrays.asList(e));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
}
