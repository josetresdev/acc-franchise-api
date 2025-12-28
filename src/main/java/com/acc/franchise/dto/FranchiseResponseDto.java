package com.acc.franchise.dto;

import java.util.UUID;

public record FranchiseResponseDto(
    UUID id,
    String name
) {
}
