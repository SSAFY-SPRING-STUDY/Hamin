package com.sbb.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

// Response DTO: 서버가 클라이언트에게 주는 출력값
// 보통 Getter만 두는 경우가 많음
// 보통 서버가 만들어서 반환만 하므로 읽기만 되면 되는 경우가 많기 때문
// 응답 객체는 한 번 만들어지면 바뀌지 않는 편이 더 안전
@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
}
