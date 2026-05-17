package com.sbb.domain.auth.controller;

import com.sbb.domain.auth.controller.dto.LoginRequest;
import com.sbb.domain.auth.controller.dto.LoginResponse;
import com.sbb.domain.auth.service.AuthService;
import com.sbb.domain.auth.util.AuthTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = null;
        try{
            response = authService.login(request);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String bearerToken){
        // Postman의 Authorization에 있는 토큰 값을 가져오는 것?
        // 가져올떄 authHeader = "token"이 아니라, authHeader = BEARER "token"의 형태로 가져오기 때문에, 뒷부분만 파싱
        // 규모가 커질수록 매번 파싱하는 것은 번거로우므로 따로 파싱 메서드를 만들어두기?

        // bearerToken은 Authorization 헤더 전체 값이다.
        // 예: "Bearer abc-123"
        // 실제 sessionStore의 Key로 쓰는 값은 앞의 "Bearer "를 제거한 sessionKey이다.
        String sessionKey = AuthTokenUtils.parseBearerToken(bearerToken);

        // 로그아웃(세션 삭제)을 위해서는 sessionStore에 저장되어 있는 Key 값인 토큰이 필요
        authService.logout(sessionKey);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}