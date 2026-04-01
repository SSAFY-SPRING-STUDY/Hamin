package com.sbb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 없는 데이터 조회 시 404 오류 반환을 위한 클래스
@ResponseStatus(HttpStatus.NOT_FOUND)
// @ResponseStatus?
// - 이 예외(또는 이 메서드)에 대해 어떤 HTTP 상태코드를 응답할지 지정하는 어노테이션
// - 예외 클래스 위에 붙이면 예외가 발생했을 때 스프링이 그 예외를 보고 HTTP 응답 상태를 결정
// HttpStatus?
// - 스프링이 제공하는 HTTP 상태코드 상수(enum)
// HttpStatus.OK → 200
// HttpStatus.CREATED → 201
// HttpStatus.NO_CONTENT → 204
// HttpStatus.NOT_FOUND → 404
// HttpStatus.INTERNAL_SERVER_ERROR → 500
// HTTP 404 상태코드인 NOT_FOUND는 "요청한 자원을 찾을 수 없음"이라는 뜻
// - URL 자체가 없는 경우 또는 요청한 데이터가 없는 경우

public class PostNotFoundException extends RuntimeException {
    // RuntimeException를 상속받는 이유?:
    // - 예외 클래스인 PostNotFoundException를 생성하되, Unchecked Exception이므로,
    // - 대표적인 RuntimeException 계열을 상속받는 것
    // 왜 런타임계열?: 게시글이 없다는 상황은 파일 입출력처럼 복구를 강제해야 하는 예외라기보다,
    // 요청 처리 중 발생한 비즈니스 예외에 가깝기 때문

    public PostNotFoundException(String message) {
        super(message);
    }
}