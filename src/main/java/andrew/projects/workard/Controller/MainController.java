package andrew.projects.workard.Controller;


import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Service.JwtUserDetailsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {
	@Autowired
    JwtUserDetailsService jwtUserDetailsService;
	@Autowired
    UserRepo userRepo;

    @GetMapping("/hello")
    public String firstPage(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ","");
        return "Hello " + new JwtTokenUtil().getUsernameFromToken(token);
    }

    @GetMapping("/admin")
    public String adminZone(HttpServletRequest req) {
        String token = req.getHeader("Authorization").replace("Bearer ","");
        return "Hi Admin, "+ new JwtTokenUtil().getUsernameFromToken(token);
    }

    @GetMapping("/activate/{code}")
    public String activateAccount(@PathVariable String code) {
        val a = userRepo.findUserByActivationCode(code);
        if (a.isPresent()) {
            a.ifPresent(account -> account.setEmailConfirmation(null));
            userRepo.save(a.get());
            return "Activated";
        }
        return "Non existing code";
    }

}