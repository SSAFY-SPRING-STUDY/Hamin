package com.sbb.domain.auth.util;

import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// private AuthTokenUtils(); 를 만들어주는 어노테이션
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenUtils {

    private static final String PREFIX_BEARER = "Bearer ";

    // bearerToken은 Authorization 헤더 전체 값이다.
    // 예: "Bearer abc-123"
    // 이 메서드는 해당 값이 Bearer 형식인지 확인한다.
    public static boolean isValidBearerToken(String bearerToken) {
        return bearerToken != null && bearerToken.startsWith(PREFIX_BEARER);
    }

    public static String parseBearerToken(String bearerToken) {
        if (isValidBearerToken(bearerToken)) {
            return bearerToken.substring(PREFIX_BEARER.length());
        }

        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }
}