package com.sbb.domain.post.controller;

import com.sbb.domain.auth.component.SessionManager;
import com.sbb.domain.auth.util.AuthTokenUtils;
import com.sbb.domain.post.controller.dto.request.PostRequest;
import com.sbb.domain.post.controller.dto.response.PostResponse;
import com.sbb.domain.post.service.PostService;
import com.sbb.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final SessionManager sessionManager;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestBody PostRequest request,
            @RequestHeader("Authorization") String bearerToken
    ) {
        // 게시글 생성은 이제 로그인한 사용자만 가능하다.
        // 따라서 요청 body에서 author를 받는 것이 아니라,
        // Authorization 헤더의 세션키를 통해 작성자를 찾는다.

        Long authorId = getMemberIdFromBearerToken(bearerToken);

        PostResponse response = postService.create(request, authorId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("게시글 생성 성공", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        // 조회 API는 명세상 인증 없이 접근 가능하다.
        List<PostResponse> response = postService.getPosts();

        return ResponseEntity
                .ok(ApiResponse.success("게시글 목록 조회 성공", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id) {
        // 단건 조회도 인증 없이 접근 가능하다.
        PostResponse response = postService.getPostById(id);

        return ResponseEntity
                .ok(ApiResponse.success("게시글 조회 성공", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequest request,
            @RequestHeader("Authorization") String bearerToken
    ) {
        // 게시글 수정은 로그인 사용자만 가능하고,
        // 그중에서도 해당 게시글 작성자만 가능하다.
        Long authorId = getMemberIdFromBearerToken(bearerToken);

        PostResponse response = postService.update(request, id, authorId);

        return ResponseEntity
                .ok(ApiResponse.success("게시글 수정 성공", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long id,
            @RequestHeader("Authorization") String bearerToken
    ) {
        // 게시글 삭제도 수정과 마찬가지로 작성자 본인만 가능하다.
        Long authorId = getMemberIdFromBearerToken(bearerToken);

        postService.delete(id, authorId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("게시글 삭제 성공"));
    }

    private Long getMemberIdFromBearerToken(String bearerToken) {
        // 공통 인증 흐름
        // 1. Bearer Token 형식 검증
        // 2. "Bearer " 제거 후 sessionKey 추출
        // 3. SessionManager에서 memberId 조회

        String sessionKey = AuthTokenUtils.parseBearerToken(bearerToken);

        return sessionManager.getMemberId(sessionKey);
    }
}