package com.acc.franchise.controller;

import com.acc.franchise.dto.FranchiseBranchRequestDto;
import com.acc.franchise.dto.FranchiseBranchResponseDto;
import com.acc.franchise.response.ApiResponse;
import com.acc.franchise.service.FranchiseBranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = FranchiseBranchController.class)
class FranchiseBranchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FranchiseBranchService branchService;

    @Test
    void shouldCreateBranchSuccessfully() {
        // given
        Long franchiseId = 123L;
        FranchiseBranchRequestDto request = new FranchiseBranchRequestDto("Branch A", franchiseId);

        Long id = 1L;
        FranchiseBranchResponseDto response = new FranchiseBranchResponseDto(id, franchiseId, "Branch A");

        when(branchService.create(any()))
                .thenReturn(Mono.just(response));

        // when / then
        webTestClient.post()
                .uri("/api/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Branch created successfully")
                .jsonPath("$.data.id").isEqualTo(id.intValue())
                .jsonPath("$.data.franchiseId").isEqualTo(franchiseId.intValue())
                .jsonPath("$.data.name").isEqualTo("Branch A");
    }
}
