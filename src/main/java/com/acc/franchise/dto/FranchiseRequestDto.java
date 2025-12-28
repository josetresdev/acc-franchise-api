package com.acc.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FranchiseRequestDto(

    @NotBlank
    @Size(min = 2, max = 120)
    String name

) {
}
