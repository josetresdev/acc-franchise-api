package com.acc.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FranchiseBranchRequestDto(
        @NotBlank String name,
        @NotNull Long franchiseId
) {}
