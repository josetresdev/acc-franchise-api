package com.acc.franchise.service;

import com.acc.franchise.dto.HealthResponseDto;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    /**
     * Method to check the health status of the API.
     * Returns a Mono containing HealthResponseDto with service status.
     * Using reactive Mono for non-blocking response.
     *
     * @return Mono of HealthResponseDto with status and service name
     */
    public Mono<HealthResponseDto> checkHealth() {
        return Mono.just(new HealthResponseDto("UP", "ACC Franchise API"));
    }
}
