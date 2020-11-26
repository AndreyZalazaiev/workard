package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Device;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Domain.Visit;
import andrew.projects.workard.Repos.DeviceRepo;
import andrew.projects.workard.Repos.UserRepo;
import andrew.projects.workard.Repos.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/visit")
@RestController
public class VisitController {
    @Autowired
    VisitRepo visitRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DeviceRepo deviceRepo;

    @PostMapping
    public ResponseEntity<?> createVisit(@RequestBody Visit v, @RequestBody int deviceCode){

        if(isInDeviceList(v.getIdRoom(),deviceCode)){
            return ResponseEntity.ok(visitRepo.save(v));
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/update")
    public ResponseEntity<?> setExitTime(@RequestBody  Visit v,@RequestBody int deviceCode){
        if(isInDeviceList(v.getIdRoom(),deviceCode)){
            v.setExitTime(LocalDateTime.now());
            return ResponseEntity.ok(visitRepo.save(v));
        }
        return ResponseEntity.badRequest().build();
    }

    public boolean isInDeviceList(int idRoom, int deviceCode){
        ArrayList<Device> deviceList=deviceRepo.findByIdRoom(idRoom);
        return deviceList.stream().filter(device -> device.getDeviceCode().equals(deviceCode)).count()>0;
    }

}
