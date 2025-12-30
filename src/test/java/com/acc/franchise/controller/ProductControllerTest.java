package com.acc.franchise.controller;

import com.acc.franchise.dto.ProductRequestDto;
import com.acc.franchise.dto.ProductResponseDto;
import com.acc.franchise.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully() {
        // given
        ProductRequestDto request = new ProductRequestDto(
                "branch-uuid-1234",
                "Product A",
                10,
                new BigDecimal("99.99")
        );

        String id = "product-id-001";
        String uid = "product-uid-001";
        ProductResponseDto response = new ProductResponseDto(
                id,
                uid,
                "branch-uuid-1234",
                "Product A",
                10,
                new BigDecimal("99.99")
        );

        when(productService.create(any()))
                .thenReturn(Mono.just(response));

        // when / then
        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.message").isEqualTo("Product created successfully")
                .jsonPath("$.data.id").isEqualTo(id)
                .jsonPath("$.data.uid").isEqualTo(uid)
                .jsonPath("$.data.franchiseBranchId").isEqualTo("branch-uuid-1234")
                .jsonPath("$.data.name").isEqualTo("Product A")
                .jsonPath("$.data.stock").isEqualTo(10)
                .jsonPath("$.data.price").isEqualTo(99.99);
    }
}
