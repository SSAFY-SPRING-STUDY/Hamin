package com.sbb.domain.auth.service;

import com.sbb.domain.auth.component.SessionManager;
import com.sbb.domain.auth.controller.dto.LoginRequest;
import com.sbb.domain.auth.controller.dto.LoginResponse;
import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.member.repository.MemberRepository;
import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // SessionManager는 로그인 후 로그인 상태를 저장하는 곳
    private final SessionManager sessionManager;
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest request) {
        // LoginRequest에서는 일반적으로 Id와 Password를 받아오는데, 이 값들은 MemberEntity에 저장되어 있음
        // 두 값을 조회해서 올바른 값이라면 로그인이 성공
        // MemberEntity는 MemberRepository에 있으므로 주입 필요?

        // ID 확인
        MemberEntity member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USERNAME));

        // Password 확인 : MemberEntity를 가져오므로, MemberEntity 안에 isValidPassword 메서드를 생성해서 사용
        if (member.checkPassword(request.password())) {
            // 만약 Password가 일치한다면 토큰을 발급
            String accessToken = sessionManager.createSession(member.getId());

            return LoginResponse.from(accessToken);
        }

        // 불일치한다면
        throw new CustomException(ErrorCode.INVALID_PASSWORD);
    }

    public void logout(String sessionKey) {
        sessionManager.removeSession(sessionKey);
    }

    public Long getMemberId(String sessionKey) {
        return sessionManager.getMemberId(sessionKey);
    }
}