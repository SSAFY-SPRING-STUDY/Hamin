package com.sbb.domain.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// private AuthTokenUtils(); 를 만들어주는 어노테이션
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenUtils {

    private static final String PREFIX_BEARER = "Bearer ";

    // 토큰 검증 로직을 메서드로 분리하면
    // Controller나 Service에서 같은 if문을 반복하지 않아도 된다.
    public static boolean isValidBearerToken(String bearerToken) {
        return bearerToken != null && bearerToken.startsWith(PREFIX_BEARER);
    }

    public static String parseBearerToken(String bearerToken) {
        if (isValidBearerToken(bearerToken)) {
            return bearerToken.substring(PREFIX_BEARER.length());
        }

        throw new IllegalArgumentException("비정상 토큰");
    }
}