package com.sbb.domain.post.entity;

import com.sbb.domain.member.entity.MemberEntity;
import lombok.Getter;

@Getter
public class PostEntity {
    private static Long AUTO_INCREMENT = 1L;

    private Long id;
    private String title;
    private String content;

    // 기존에는 작성자를 문자열로 저장했을 가능성이 높다.
    // 변경 전 예시: private String author;
    //
    // day4에서는 게시글 작성자를 단순 문자열이 아니라 MemberEntity로 연결한다.
    // 이렇게 하면 게시글 작성자의 id, username, nickname을 함께 사용할 수 있고,
    // 수정/삭제 권한 검증도 author.getId()로 명확하게 처리할 수 있다.
    private MemberEntity author;

    private PostEntity(Long id, String title, String content, MemberEntity author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static PostEntity create(String title, String content, MemberEntity author) {
        return new PostEntity(
                AUTO_INCREMENT++,
                title,
                content,
                author
        );
    }

    public void update(String title, String content) {
        // 게시글 수정에서는 id와 author는 바꾸지 않는다.
        // 작성자는 최초 생성 시점에 정해지고, 수정은 제목/내용만 바꾸는 것이 자연스럽다.
        this.title = title;
        this.content = content;
    }
}