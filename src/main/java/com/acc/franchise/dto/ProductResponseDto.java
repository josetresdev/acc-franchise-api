package com.acc.franchise.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String id,
        Long franchiseBranchId,
        String name,
        int stock,
        BigDecimal price
) {}
