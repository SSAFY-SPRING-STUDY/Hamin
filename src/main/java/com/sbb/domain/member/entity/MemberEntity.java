package com.sbb.domain.member.entity;

import lombok.Getter;

@Getter
public class MemberEntity {
    private static long AUTO_INCREMENT = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;

    private MemberEntity(Long id, String username, String password, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberEntity create(String username, String password, String nickname) {
        // 생성자를 private으로 막고 create()를 사용하면
        // 객체 생성 규칙을 한 곳에서 통제할 수 있다.
        // 여기서는 id 자동 증가 규칙을 create() 안에 모아둔다.
        return new MemberEntity(
                AUTO_INCREMENT++,
                username,
                password,
                nickname
        );
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}