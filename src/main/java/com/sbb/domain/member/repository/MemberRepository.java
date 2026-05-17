package com.sbb.domain.member.repository;

import com.sbb.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // JpaRepository는 save(), findById(), findAll(), delete() 등을 기본 제공한다.
    // findByUsername()은 메서드 이름을 보고 Spring Data JPA가 자동으로 쿼리를 만들어준다.
    Optional<MemberEntity> findByUsername(String username);
}