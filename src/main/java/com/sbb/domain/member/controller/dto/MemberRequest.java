package com.sbb.domain.member.controller.dto;

// Record의 특징?
// @Getter, @RequiredArgsConstructor 어노테이션이 필요 없다(기본적으로 내장)
// private final도 모든 필드명에 지원
// 대신 코드 끝에 ;이 아니라 ,를 붙여야 함
// DTO에서는 Record를 쓰지만, Entity에서는 안의 내용들이 수정될 수 있기 때문에 Record로 작성 X
// 접근 시에는 MemberRequest request에서 request.getLoginId()가 아니라 request.LoginId()로 접근

//@Getter
//@RequiredArgsConstructor
//public class MemberRequest{
//    private final String loginId;
//    private final String password;
//    private final String name;
//} 이것과 같음

import com.sbb.domain.member.entity.MemberEntity;

public record MemberRequest(
        String username,
        String password,
        String nickname
) {
    // 정적 팩토리 메서드
    // DTO를 Entity로 바꾸는 책임을 DTO 안에 둘 수도 있다.
    // 단, 실무에서는 Service나 별도 Mapper 클래스로 분리하는 경우도 많다.
    public MemberEntity toEntity(){
        return MemberEntity.create(
                username,
                password,
                nickname
        );
    }
}