package com.acc.franchise.service;

import com.acc.franchise.dto.HealthResponseDto;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthResponseDto checkHealth() {
        // Returns the current health status of the application
        return new HealthResponseDto("UP", "ACC Franchise API");
    }
}
