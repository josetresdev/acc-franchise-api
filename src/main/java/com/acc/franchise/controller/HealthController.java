package com.acc.franchise.controller;

import com.acc.franchise.dto.HealthResponseDto;
import com.acc.franchise.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public HealthResponseDto healthCheck() {
        return healthService.checkHealth();
    }
}
