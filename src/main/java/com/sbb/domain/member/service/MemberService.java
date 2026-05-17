package com.sbb.domain.member.service;

import com.sbb.domain.member.controller.dto.MemberRequest;
import com.sbb.domain.member.controller.dto.MemberResponse;
import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.member.repository.MemberRepository;
import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse join(MemberRequest request) {
        MemberEntity member = request.toEntity();

        // day4까지는 Map에 저장했지만,
        // day5부터 save()는 실제 DB에 insert 또는 update를 수행한다.
        MemberEntity savedMember = memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }

    public MemberResponse getMemberInfo(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        return MemberResponse.from(member);
    }
}