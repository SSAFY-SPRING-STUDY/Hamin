package com.sbb.domain.member.repository;

import com.sbb.domain.member.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    // ConcurrentHashMap은 멀티스레드 환경에서 안전한 HashMap
    // 다양한 사람들이 한번에 조회하거나 수정하는 다중 요청을 버킷으로 나눠서 관리를 편하게 해준다.
    // 현재는 DB 없이 Map을 임시 저장소처럼 사용하는 구조이다.
    // 나중에 DB를 연결하면 이 Repository의 내부 구현이 MyBatis/JPA 방식으로 바뀔 수 있다.
    private static final Map<Long, MemberEntity> memberStore = new ConcurrentHashMap<>();

    public MemberEntity save(MemberEntity member) {
        memberStore.put(member.getId(), member);
        MemberEntity savedMember = memberStore.get(member.getId());

        return savedMember;
    }

    public Optional<MemberEntity> findByUsername(String username) {
        for (MemberEntity member : memberStore.values()){
            if (member.getUsername().equals(username))
                return Optional.of(member);
        }
        return Optional.empty();
    }

    public Optional<MemberEntity> findById(Long memberId) {
        return Optional.ofNullable(memberStore.get(memberId));
    }
}