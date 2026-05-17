package com.sbb.domain.auth.component;

import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    // 유효 아이디를 통해 발급받는 토큰 값을 Key, MemberEntity의 id 값을 Value로 해서 저장
    // Key: 세션키, Value: 로그인한 회원의 id
    private final Map<String, Long> sessionStore = new ConcurrentHashMap<>();

    public String createSession(Long memberId) {
        // UUID를 통해 랜덤 토큰을 만들고 String 값으로 반환하여 고유한 세션키를 생성
        String sessionKey = UUID.randomUUID().toString();

        sessionStore.put(sessionKey, memberId);

        return sessionKey;
    }

    public void removeSession(String sessionKey) {
        sessionStore.remove(sessionKey);
    }

    public Long getMemberId(String sessionKey) {
        Long memberId = sessionStore.get(sessionKey);

        if (memberId == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        return memberId;
    }
}