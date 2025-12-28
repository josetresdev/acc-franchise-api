package com.acc.franchise;

import com.acc.franchise.controller.FranchiseController;
import com.acc.franchise.service.FranchiseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = FranchiseController.class) // Solo carga FranchiseController
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseService franchiseService; // Mock del servicio inyectado

    @Test
    void testHealthCheck() {
        // Si FranchiseController tiene endpoints que devuelven Mono/Flux, mockéalos aquí
        // Por ejemplo, un endpoint "/api/franchise/health" de prueba
        /*
        when(franchiseService.checkHealth())
            .thenReturn(Mono.just(new HealthResponseDto("UP", "ACC Franchise API")));

        webTestClient.get()
            .uri("/api/franchise/health")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.status").isEqualTo("UP")
            .jsonPath("$.name").isEqualTo("ACC Franchise API");
        */
    }
}
