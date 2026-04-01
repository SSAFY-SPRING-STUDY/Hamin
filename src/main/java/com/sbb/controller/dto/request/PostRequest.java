package com.sbb.controller.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Request DTO: 클라이언트가 서버로 보내는 입력값을 담는 객체
// Getter, Setter 자주 사용 (lombok)
// 하지만 생성자로 주입하는 것이 좀더 나은 방법?
//스프링이 JSON 요청 바디를 객체로 바꿔 넣을 때 필드에 값을 채워야 하므로
@Getter
@RequiredArgsConstructor
public class PostRequest {
    private final String title;
    private final String content;
    private final String author;

    // @RequiredArgsConstructor가 생성해주는 것
//    public PostRequest(String title, String content, String author) {
//        this.title = title;
//        this.content = content;
//        this. author = author;
//    }
}
