package com.sbb.domain.post.entity;

import com.sbb.domain.member.entity.MemberEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity author;

    private PostEntity(String title, String content, MemberEntity author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static PostEntity create(String title, String content, MemberEntity author) {
        // 게시글 id 역시 JPA가 자동 생성한다.
        // 작성자 author는 로그인 토큰에서 찾은 MemberEntity를 넣는다.
        return new PostEntity(
                title,
                content,
                author
        );
    }

    public void update(String title, String content) {
        // JPA에서는 트랜잭션 안에서 엔티티 값을 변경하면,
        // save()를 직접 호출하지 않아도 dirty checking으로 DB에 반영된다.
        this.title = title;
        this.content = content;
    }
}