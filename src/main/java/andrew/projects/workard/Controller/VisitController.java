package andrew.projects.workard.Controller;

import andrew.projects.workard.Config.JwtTokenUtil;
import andrew.projects.workard.Domain.Device;
import andrew.projects.workard.Domain.Employee;
import andrew.projects.workard.Domain.User;
import andrew.projects.workard.Domain.Visit;
import andrew.projects.workard.Repos.*;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RequestMapping("/visit")
@RestController
public class VisitController {
    @Autowired
    VisitRepo visitRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DeviceRepo deviceRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    CompanyRepo companyRepo;

    @GetMapping("/hotspot")
    public ResponseEntity<?> hotspot(HttpServletRequest req, @RequestParam int idCompany) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (currentUser.getCompanies().stream().filter(c -> c.getId() == idCompany).count() > 0) {
            return ResponseEntity.ok(visitRepo.findHotPoints(idCompany));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<?> roomStats(HttpServletRequest req, @RequestParam int idRoom) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (RoomController.isOwnerOfRoom(idRoom, currentUser)) {
            return ResponseEntity.ok(visitRepo.findAllByIdRoom(idRoom));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> createVisit(@RequestParam String RFIDtag, @RequestParam String deviceCode, @RequestParam int idRoom) {
        Optional<Employee> emp = employeeRepo.findEmployeeByRFIDtag(RFIDtag);
        if (emp.isPresent() && isInDeviceList(idRoom, deviceCode)) {

            ArrayList<Visit> visits = editCurrentVisits(emp, idRoom);
            visitRepo.saveAll(visits);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Non authorized device");
    }

    public ArrayList<Visit> editCurrentVisits(Optional<Employee> emp, int idRoom) {
        val visits = visitRepo.findAllByIdEmployeeAndIdRoomOrderByEntryTimeDesc(emp.get().getId(), idRoom);
        if (visits.size() > 0) {
            if (visits.get(0).getExitTime() == null) {
                visits.get(0).setExitTime(LocalDateTime.now());
            } else {
                Visit v = new Visit();
                v.setIdEmployee(emp.get().getId());
                v.setEntryTime(LocalDateTime.now());
                v.setIdRoom(idRoom);
                visits.add(v);
            }
        } else {
            Visit v = new Visit();
            v.setIdEmployee(emp.get().getId());
            v.setEntryTime(LocalDateTime.now());
            v.setIdRoom(idRoom);
            visits.add(v);
        }
        return visits;
    }

    public boolean isInDeviceList(int idRoom, String deviceCode) {
        ArrayList<Device> deviceList = deviceRepo.findByIdRoom(idRoom);
        return deviceList.stream().filter(device -> device.getDeviceCode().equals(deviceCode)).count() > 0;
    }


}

