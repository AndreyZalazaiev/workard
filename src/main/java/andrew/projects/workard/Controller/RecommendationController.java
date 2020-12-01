package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Room;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Domain.Visit;
import andrew.projects.workard.Repos.RecommendationRepo;
import andrew.projects.workard.Repos.RoomRepo;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Service.RecommendationService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    public static final int PERIOD_OF_GATHERING = 3;
    LocalDateTime period ;
    final UserRepo userRepo;
    final RecommendationRepo recommendationRepo;
    final RecommendationService recommendationService;
    final RoomRepo roomRepo;

    public RecommendationController(RecommendationRepo recommendationRepo, UserRepo userRepo, RoomRepo roomRepo) {
        this.recommendationRepo = recommendationRepo;
        recommendationService = new RecommendationService();
        this.userRepo = userRepo;
        period= LocalDateTime.now().minusDays(PERIOD_OF_GATHERING);
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public ResponseEntity<?> getRecommendations(HttpServletRequest req, @RequestParam int idCompany) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if (currentUser.getCompanies().stream().filter(c -> c.getId().equals(idCompany)).count() > 0) {

            ArrayList<Visit> visits = recommendationRepo.getVisitsForThePeriod(idCompany, period);
            ArrayList<Room> rooms =roomRepo.getAllByIdCompany(idCompany);

            val res =recommendationRepo.saveAll(recommendationService.generateRecommendations(visits,rooms));

            return ResponseEntity.ok(res);
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
}
