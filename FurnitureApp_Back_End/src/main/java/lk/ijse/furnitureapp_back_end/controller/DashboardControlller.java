package lk.ijse.furnitureapp_back_end.controller;

import lk.ijse.furnitureapp_back_end.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardControlller {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/counts")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Map<String, Long>> getCounts() {
        System.out.println("Inside getCounts");
        Map<String, Long> counts = dashboardService.getDashboardCounts();
        System.out.println(counts);
        return ResponseEntity.ok(counts);
    }
}
