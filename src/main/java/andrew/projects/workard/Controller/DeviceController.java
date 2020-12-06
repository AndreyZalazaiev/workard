package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Device;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Repos.DeviceRepo;
import andrew.projects.workard.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RequestMapping("/device")
@RestController
public class DeviceController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    DeviceRepo deviceRepo;

    @PostMapping
    public ResponseEntity<?> setDevice(HttpServletRequest req, @RequestBody Device device) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();//
        if (RoomController.isCompanyOwner(device.getIdRoom(), currentUser)) {
            return ResponseEntity.ok(deviceRepo.save(device));
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDevice(HttpServletRequest req, @RequestParam String deviceCode) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        Optional<Device> storedDevice = deviceRepo.findById(deviceCode);

        if (storedDevice.isPresent()) {
            if (RoomController.isCompanyOwner(storedDevice.get().getIdRoom(), currentUser)) {
                deviceRepo.deleteInBatch(Arrays.asList(storedDevice.get()));
                return ResponseEntity.ok("Deleted");
            }
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }
}
