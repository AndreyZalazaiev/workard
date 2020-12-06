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

    public static boolean isCompanyOwner(int idRoom, User user) {
        for (Company c : user.getCompanies()
        ) {
            if (c.getRooms().stream().filter(room -> room.getId() == idRoom).count() > 0) {
                return true;
            }
        }
        return false;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(HttpServletRequest req, @RequestBody Room room) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (isCompanyOwner(room.getId(), currentUser)) {
            roomRepo.deleteInBatch(Arrays.asList(room));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    @PostMapping
    public ResponseEntity<?> createRoom( @RequestBody Room r) {
            return ResponseEntity.ok(roomRepo.save(r));
    }
}
