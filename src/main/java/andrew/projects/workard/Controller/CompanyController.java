package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Company;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.CompanyRepo;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Service.LocaleService;
import groovy.util.logging.Slf4j;
import lombok.extern.java.Log;
import org.codehaus.groovy.tools.shell.util.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

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
        return ResponseEntity.ok(companyRepo.save(company));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (current.getCompanies().contains(company)) {
            companyRepo.delete(company);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
}
