package com.sbb.domain.member.controller.dto;

import com.sbb.domain.member.entity.MemberEntity;

public record MemberRequest(
        String username,
        String password,
        String nickname
) {
    // 정적 팩토리 메서드
    public MemberEntity toEntity() {
        return MemberEntity.create(
                username,
                password,
                nickname
        );
    }
}