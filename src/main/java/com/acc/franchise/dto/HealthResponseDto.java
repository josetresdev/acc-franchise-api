package com.acc.franchise.dto;

public class HealthResponseDto {

    private final String status;
    private final String service;

    public HealthResponseDto(String status, String service) {
        this.status = status;
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public String getService() {
        return service;
    }
}
