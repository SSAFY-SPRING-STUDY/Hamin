package com.sbb.global.exception;

import com.sbb.global.exception.error.ErrorCode;
import lombok.Getter;

// 프로젝트에서 직접 정의한 예외 클래스
// RuntimeException만 던지면 어떤 HTTP 상태 코드로 응답해야 하는지 알기 어렵다.
// CustomException 안에 ErrorCode를 넣어두면,
// GlobalExceptionHandler가 ErrorCode를 보고 응답 상태와 메시지를 결정할 수 있다.
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        // 부모 RuntimeException에도 메시지를 넣어준다.
        // 이렇게 하면 로그를 찍을 때도 에러 메시지를 확인하기 쉽다.
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}