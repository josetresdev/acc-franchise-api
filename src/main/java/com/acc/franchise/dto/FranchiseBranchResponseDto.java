package com.acc.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FranchiseBranchResponseDto(
        String id,
        String uid,
        String franchiseId,
        String name
) {}
