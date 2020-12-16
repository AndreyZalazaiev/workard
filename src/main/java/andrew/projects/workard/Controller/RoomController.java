package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.*;
import andrew.projects.workard.Repos.DeviceRepo;
import andrew.projects.workard.Repos.RoomRepo;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Repos.VisitRepo;
import lombok.val;
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
    @Autowired
    DeviceRepo deviceRepo;
    @Autowired
    VisitRepo visitRepo;

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(HttpServletRequest req, @RequestBody Room room) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (isOwnerOfRoom(room.getId(), currentUser)) {
            Room r=roomRepo.findById(room.getId()).get();
            deviceRepo.deleteAll(r.getDevices());
            visitRepo.deleteAll(r.getVisits());

            roomRepo.deleteInBatch(Arrays.asList(room));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
    @GetMapping
    public ResponseEntity<?> getRooms(HttpServletRequest req, @RequestParam int idCompany)
    {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if(currentUser.getCompanies().stream().anyMatch(c -> c.getId() == idCompany)) {
            return ResponseEntity.ok(roomRepo.getAllByIdCompany(idCompany));
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
                deviceRepo.deleteAll(stored.getDevices());
                deviceRepo.saveAll(r.getDevices());
                return ResponseEntity.ok(roomRepo.save(stored));
            }
            return ResponseEntity.ok(roomRepo.save(r));
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    private boolean hasRightsToManipulateCompany(@RequestBody Room r, User currentUser) {
        return currentUser.getCompanies().stream().anyMatch(c -> c.getId().equals(r.getIdCompany()));
    }

    public static boolean isOwnerOfRoom(int idRoom, User user) {
        for (Company c : user.getCompanies()
        ) {
            if (c.getRooms().stream().anyMatch(room -> room.getId() == idRoom)) {
                return true;
            }
        }
        return false;
    }

}
