package com.sbb.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

// Request DTO: 클라이언트가 서버로 보내는 입력값을 담는 객체
// Getter, Setter 자주 사용 (lombok)
//스프링이 JSON 요청 바디를 객체로 바꿔 넣을 때 필드에 값을 채워야 하므로
@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private String author;
}
