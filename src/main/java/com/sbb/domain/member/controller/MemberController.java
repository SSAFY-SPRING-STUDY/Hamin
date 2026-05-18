package com.sbb.domain.member.controller;

import com.sbb.domain.auth.service.AuthService;
import com.sbb.domain.auth.util.AuthTokenUtils;
import com.sbb.domain.member.controller.dto.MemberRequest;
import com.sbb.domain.member.controller.dto.MemberResponse;
import com.sbb.domain.member.service.MemberService;
import com.sbb.domain.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping()
    public ResponseEntity<ApiResponse<MemberResponse>> join(@RequestBody MemberRequest request) {
        MemberResponse response = memberService.join(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입 성공", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberResponse>> getMyInfo(@RequestHeader("Authorization") String bearerToken) {
        // sessionStore에 저장된 Id를 받아와 해당 Id에 맞는 MemberEntity를 MemberRepository로부터 가져오고,
        // MemberResponse로 바꾸어 사용자에게 전달

        String sessionKey = AuthTokenUtils.parseBearerToken(bearerToken);
        Long memberId = authService.getMemberId(sessionKey);

        MemberResponse response = memberService.getMemberInfo(memberId);

        return ResponseEntity
                .ok(ApiResponse.success("내 정보 조회 성공", response));
    }
}