package com.sbb.entity;

import lombok.Getter;
import lombok.Setter;

// Entity: 실제 저장되는 도메인 객체, 실무에서 가장 중요
// Setter를 최소화 하는 경우가 많음
// 아무 곳에서나 값 변경 가능하면 객체 일관성이 깨지고, 비즈니스 규칙 통제가 어려워지기 때문
// -> 실무에서는 꼭 필요한 필드만 변경 메서드 제공, 생성자를 제한하며 Setter를 최소화
@Getter
@Setter
public class PostEntity {
    private Long id;
    private String title;
    private String content;
    private String author;
}
