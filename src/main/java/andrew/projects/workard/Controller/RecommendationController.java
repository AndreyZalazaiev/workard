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
    private static final int PERIOD_OF_GATHERING = 3;
    private final UserRepo userRepo;
    private final RecommendationRepo recommendationRepo;
    private final RecommendationService recommendationService;
    private final RoomRepo roomRepo;
    private LocalDateTime period;

    public RecommendationController(RecommendationRepo recommendationRepo, UserRepo userRepo, RoomRepo roomRepo) {
        this.recommendationRepo = recommendationRepo;
        recommendationService = new RecommendationService();
        this.userRepo = userRepo;
        period = LocalDateTime.now().minusDays(PERIOD_OF_GATHERING);
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public ResponseEntity<?> getRecommendations(HttpServletRequest req, @RequestParam int idCompany) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if (hasRightsToGetRecom(idCompany, currentUser)) {

            val recommendations = recommendationRepo.getAllByDateIsAfterAndIdCompany(LocalDateTime.now().minusDays(1), idCompany);
            if (recommendations.size() == 0) {

                ArrayList<Visit> visits = recommendationRepo.getVisitsForThePeriod(idCompany, period);
                ArrayList<Room> rooms = roomRepo.getAllByIdCompany(idCompany);

                val res = recommendationRepo.saveAll(recommendationService.generateRecommendations(visits, rooms));

                return ResponseEntity.ok(res);
            }
            return ResponseEntity.ok("Already generated for today");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    private boolean hasRightsToGetRecom(@RequestParam int idCompany, User currentUser) {
        return currentUser.getCompanies().stream().anyMatch(c -> c.getId().equals(idCompany));
    }
}
