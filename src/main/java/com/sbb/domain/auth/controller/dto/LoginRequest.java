package com.sbb.domain.auth.controller.dto;

public record LoginRequest(
        String username,
        String password
) {
}