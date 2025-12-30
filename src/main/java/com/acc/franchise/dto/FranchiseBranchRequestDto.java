package com.acc.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FranchiseBranchRequestDto(
        @NotBlank String franchiseId,
        @NotBlank
        @Size(min = 2, max = 120)
        String name
) {}
