package com.sbb.global.exception.handler;

import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import com.sbb.domain.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 모든 Controller에서 발생하는 예외를 한 곳에서 처리하는 클래스
// @RestControllerAdvice는 여러 Controller에 흩어진 try-catch를 줄여준다.
// 즉, Controller는 정상 흐름에 집중하고, 예외 응답은 여기서 일괄 처리한다.
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        // 예상하지 못한 예외는 서버 내부 오류로 처리한다.
        // 실제 서비스에서는 반드시 로그를 남겨야 원인을 추적할 수 있다.
        log.error("Unexpected exception occurred", e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}