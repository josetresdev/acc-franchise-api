package com.acc.franchise.controller;

import com.acc.franchise.dto.HealthResponseDto;
import com.acc.franchise.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    /**
     * Constructor injection for HealthService.
     *
     * @param healthService instance of HealthService
     */
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    // ===================== Health Check Endpoint =====================
    /**
     * Endpoint to check the health status of the API.
     * Returns a reactive Mono containing HealthResponseDto with service status.
     *
     * @return Mono<HealthResponseDto> indicating API health and service name
     */
    @GetMapping
    public Mono<HealthResponseDto> healthCheck() {
        return healthService.checkHealth();
    }
}
