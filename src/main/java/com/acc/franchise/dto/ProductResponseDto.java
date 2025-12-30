package com.acc.franchise.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        String id,
        String uid,
        String franchiseBranchId,
        String name,
        int stock,
        BigDecimal price
) {}
