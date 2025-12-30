package com.acc.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequestDto(
        @NotNull Long franchiseBranchId,
        @NotBlank @Size(min = 2, max = 120) String name,
        @NotNull @Positive int stock,
        @NotNull @Positive BigDecimal price
) {}
