package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Company;
import andrew.projects.workard.Domain.Room;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.RoomRepo;
import andrew.projects.workard.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoomRepo roomRepo;

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(HttpServletRequest req, @RequestBody Room room) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (isOwnerOfRoom(room.getId(), currentUser)) {
            roomRepo.deleteInBatch(Arrays.asList(room));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    @PostMapping
    public ResponseEntity<?> createRoom(HttpServletRequest req, @RequestBody Room r) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (hasRightsToManipulateCompany(r, currentUser)) {
            if (r.getId() != null) {
                Room stored = roomRepo.findById(r.getId()).get();
                stored.setName(r.getName());
                stored.setRecommendedValue(r.getRecommendedValue());
                return ResponseEntity.ok(roomRepo.save(stored));
            }
            return ResponseEntity.ok(roomRepo.save(r));
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    private boolean hasRightsToManipulateCompany(@RequestBody Room r, User currentUser) {
        return currentUser.getCompanies().stream().filter(c->c.getId()==r.getIdCompany()).count()>0;
    }

    public static boolean isOwnerOfRoom(int idRoom, User user) {
        for (Company c : user.getCompanies()
        ) {
            if (c.getRooms().stream().filter(room -> room.getId() == idRoom).count() > 0) {
                return true;
            }
        }
        return false;
    }

}
