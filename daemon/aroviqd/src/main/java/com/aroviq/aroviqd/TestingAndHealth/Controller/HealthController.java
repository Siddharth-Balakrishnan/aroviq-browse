package com.aroviq.aroviqd.TestingAndHealth.Controller;

import com.aroviq.aroviqd.TestingAndHealth.Dto.EndpointHealth;
import com.aroviq.aroviqd.TestingAndHealth.Service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/status")
    public Map<String, String> getHealthStatus() {
        return Map.of(
                "status", healthService.getOverallHealth(),
                "message", "Aroviq Daemon is running",
                "timestamp", String.valueOf(System.currentTimeMillis()));
    }

    @GetMapping("/discovery")
    public List<EndpointHealth> getEndpointHealth() {
        return healthService.discoverEndpoints();
    }
}
