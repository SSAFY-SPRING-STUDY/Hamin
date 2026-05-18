package com.sbb.domain;

import com.sbb.global.exception.error.ErrorCode;

// 모든 API 응답 형식을 통일하기 위한 공통 응답 DTO
// 지금까지는 Controller마다 ResponseEntity<MemberResponse>, ResponseEntity<PostResponse>처럼
// 응답 형태가 제각각이었다.
// ApiResponse<T>를 사용하면 성공/실패 응답의 모양을 일정하게 만들 수 있다.
public record ApiResponse<T>(
        String message,
        T data
) {
    // 성공 응답 - 데이터가 있는 경우
    // 예: 게시글 생성 성공 + 생성된 게시글 데이터
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    // 성공 응답 - 데이터가 없는 경우
    // 예: 게시글 삭제 성공
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(message, null);
    }

    // 에러 응답 - 데이터가 없는 경우
    // 예: 로그인 필요, 게시글 없음, 권한 없음
    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getMessage(), null);
    }

    // 에러 응답 - 추가 데이터를 담고 싶은 경우
    // 현재 과제에서는 거의 사용하지 않아도 되지만, 명세에 맞춰 작성한다.
    public static <T> ApiResponse<T> error(ErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode.getMessage(), data);
    }
}