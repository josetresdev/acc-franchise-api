package com.acc.franchise.controller;

import com.acc.franchise.dto.HealthResponseDto;
import com.acc.franchise.service.HealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = HealthController.class)
class HealthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HealthService healthService;

    @Test
    void testHealthCheck() {
        when(healthService.checkHealth())
            .thenReturn(Mono.just(new HealthResponseDto("UP", "ACC Franchise API")));

        webTestClient.get()
            .uri("/api/health")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.status").isEqualTo("UP")
            .jsonPath("$.name").isEqualTo("ACC Franchise API");
    }
}
