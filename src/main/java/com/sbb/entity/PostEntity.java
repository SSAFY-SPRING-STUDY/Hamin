package com.sbb.entity;

import lombok.Getter;

// Entity: 실제 저장되는 도메인 객체, 실무에서 가장 중요
// Setter를 최소화 하는 경우가 많음
// 아무 곳에서나 값 변경 가능하면 객체 일관성이 깨지고, 비즈니스 규칙 통제가 어려워지기 때문
// -> 실무에서는 꼭 필요한 필드만 변경 메서드 제공, 생성자를 제한하며 Setter를 최소화
@Getter
//@RequiredArgsConstructor 여기서는 안쓴다? this.id = AUTO_INCREMENT++; 로직이 필요하기 때문에?
public class PostEntity {

    private static Long AUTO_INCREMENT = 1L;

    private Long id;
    private String title;
    private String content;
    private String author;

    // Setter를 이용하면 이후에 ID를 변경할 수 있기 때문에 SOLID의 단일 책임 원칙을 위해 생성자로 필드 주입?
    public PostEntity(String title, String content, String author) {
        this.id = AUTO_INCREMENT++;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // private인 필드들을 수정하기 위해서 클래스 내에 update 메서드를 구현
    public void update(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
