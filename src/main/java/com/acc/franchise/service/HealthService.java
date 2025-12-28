package com.acc.franchise.service;

import com.acc.franchise.dto.HealthResponseDto;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public Mono<HealthResponseDto> checkHealth() {
        return Mono.just(new HealthResponseDto("UP", "ACC Franchise API"));
    }
}
