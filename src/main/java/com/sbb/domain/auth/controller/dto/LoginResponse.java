package com.sbb.domain.auth.controller.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
    // 정적 팩토리 메서드를 사용하면
    // tokenType처럼 항상 같은 값을 넣어야 하는 부분을 한 곳에서 관리할 수 있다.
    public static LoginResponse from(String accessToken) {
        return new LoginResponse(accessToken, "Bearer ");
    }
}