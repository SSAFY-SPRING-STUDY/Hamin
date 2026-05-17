package com.sbb.domain.member.service;

import com.sbb.domain.member.controller.dto.MemberRequest;
import com.sbb.domain.member.controller.dto.MemberResponse;
import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse join(MemberRequest request) {
        MemberEntity member = request.toEntity();
        MemberEntity savedMember = memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }

    public MemberResponse getMemberInfo(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다.")
        );
        return MemberResponse.from(member);
    }
}