package com.sbb.domain.auth.component;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    // 유효 아이디를 통해 발급받는 토큰 값을 Key, MemberEntity의 id 값을 Value로 해서 저장
    // 여기서 Key는 클라이언트에게 전달되는 세션키이고,
    // Value는 서버가 내부적으로 기억해야 하는 회원의 id 값이다.
    private final Map<String, Long> sessionStore = new ConcurrentHashMap<>();

    public String createSession(Long memberId) {
        // UUID를 통해 랜덤 토큰을 만들고 String 값으로 반환하여 고유한 세션키를 생성
        String sessionKey = UUID.randomUUID().toString();

        // 로그인에 성공하면 "이 세션키는 이 회원의 로그인 상태다"라고 서버에 기록
        sessionStore.put(sessionKey, memberId);

        return sessionKey;
    }

    public void removeSession(String sessionKey) {
        sessionStore.remove(sessionKey);
    }

    public Optional<Long> getMemberId(String sessionKey) {
        return Optional.ofNullable(sessionStore.get(sessionKey));
    }
}