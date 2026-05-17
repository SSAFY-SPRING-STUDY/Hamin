package com.sbb.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String nickname;

    private MemberEntity(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberEntity create(String username, String password, String nickname) {
        // day4까지는 AUTO_INCREMENT 값을 직접 증가시키며 id를 만들었다.
        // day5부터는 JPA가 @GeneratedValue를 보고 DB에 저장할 때 id를 자동 생성한다.
        // 따라서 create()에서는 id를 직접 넣지 않는다.
        return new MemberEntity(
                username,
                password,
                nickname
        );
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}