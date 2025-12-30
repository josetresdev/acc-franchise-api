package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseRequestDto;
import com.acc.franchise.dto.FranchiseResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.service.FranchiseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseController.class)
class FranchiseControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseService franchiseService;

    @Test
    void shouldCreateFranchiseSuccessfully() {
        // given
        FranchiseRequestDto request = new FranchiseRequestDto("McDonalds");

        Long id = 1L;
        FranchiseResponseDto response = new FranchiseResponseDto(id, "McDonalds");

        when(franchiseService.create(any()))
                .thenReturn(Mono.just(response));

        // when / then
        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Franchise created successfully")
                .jsonPath("$.data.id").isEqualTo(id.intValue())
                .jsonPath("$.data.name").isEqualTo("McDonalds");
    }
}
