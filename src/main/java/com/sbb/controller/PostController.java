package com.sbb.controller;

// Controller 계층의 역할
// - URL 받기
// - HTTP Method 구분하기
// - RequestBody / PathVariable 받기
// - Service 호출하기
// - HTTP 상태코드와 응답 반환하기

import com.sbb.controller.dto.request.PostRequest;
import com.sbb.controller.dto.response.PostResponse;
import com.sbb.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// ResponseEntity: HTTP 응답(Body, Header, Status Code)을 개발자가 직접 제어하여 반환할 수 있게 해주는 클래스
// 그냥 객체만 반환하면 상태코드를 조절하기 어렵다?
// 상태코드 ex) 201 Created(생성), 200 OK(조회), 204 No Content(삭제)
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
// final 필드인 postService를 위한 생성자 생성
@RestController
// @RestController: 이 클래스가 REST API 컨트롤러임을 의미,
// 즉 메서드 반환값을 화면(view)으로 보내는 게 아니라 JSON 응답으로 보내는 컨트롤러
@RequestMapping("/api/posts")
// @RequestMapping("/api/posts"): 이 컨트롤러의 공통 URL 시작점
// 클래스 위에 이걸 붙이면 각 메서드는 /api/posts를 기준으로 붙음
// 추가적인 url이 필요하다면 각 메서드에 ("/{id}") 같은 형식을 추가
public class PostController {
    // PostService를 사용해야 하므로 Service를 주입
    private final PostService postService;

    // 게시글 등록
    @PostMapping
    // @PostMapping: POST 요청 처리
    // @RequestBody: 클라이언트가 JSON으로 보낸 본문을 객체로 변환(JSON 응답용)
    // POST 요청 받기 -> 요청 본문(JSON)을 PostRequest로 받기 -> 저장 후 결과를 반환
    // .status(HttpStatus.CREATED): HTTP 응답의 상태 코드를 201 Created로 명시적으로 설정
    // .body(response):
    // - 클라이언트에게 반환할 실제 데이터(response 객체)를 HTTP 응답의 본문(Body)에 담고,
    // - 최종적인 ResponseEntity 객체를 완성하여 반환
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        PostResponse response = postService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    // @GetMapping: GET 요청 처리
    // GET 요청 받기 -> 전체 게시글 목록 반환
    // .ok(responses):
    // - 스프링이 제공하는 정적 팩토리 메서드(편의 메서드)
    // - 내부적으로 ResponseEntity.status(HttpStatus.OK).body(responses)와 동일하게 동작
    // - 즉 위의 .status(HttpStatus.CREATED).body(response)에서 CREATED만 OK로 바꿔 처리
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        List<PostResponse> responses = postService.findAll();
        return ResponseEntity.ok(responses);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    // @PathVariable: URL 경로의 값을 변수로 받기
    // GET 요청 받기 -> URL 경로의 id를 받기 -> 해당 게시글 하나 조회
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long id) {
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    // @PutMapping: PUT 요청 처리
    // PUT 요청 받기 -> URL 경로의 id와 요청 본문(JSON)을 받기 -> 해당 게시글 수정
    // .ok():
    // - 상태 코드를 200 OK로 설정
    // - 데이터 없이 ResponseEntity.ok()로만 적으면 스프링 내부적으로 BodyBuilder 라는 객체 반환
    // - ResponseEntity.ok(data): 200 OK 상태 코드와 본문(Body)을 합쳐서 응답 객체 생성
    // - ResponseEntity.ok(): 상태 코드는 200 OK로 결정, 본문(Body)을 넣을지 말지 결정하는 빌더(Builder) 단계를 시작
    //      - 본문에 넣을 데이터가 있는 경우: .body(data)를 호출하여 본문을 채우면서 동시에 ResponseEntity 객체를 생성
    //      - 본문에 넣을 데이터가 없는 경우 (Void): 데이터가 없는 상태로 ResponseEntity 객체를 생성하도록 명령(.build())
    // .build():
    // - ResponseEntity 객체의 구성을 완료하고 생성하는 메서드
    // - 메서드 반환 타입이 <Void>이므로 응답 본문(Body)에 담을 데이터가 없기 때문에 .body() 메서드를 호출하지 않고,
    // - 상태 코드만 설정한 상태에서 응답 객체를 완성(build)하여 반환
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostRequest request) {
        postService.update(id, request);
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    // @DeleteMapping: DELETE 요청 처리
    // DELETE 요청 받기 -> URL 경로의 id를 받기 -> 해당 게시글 삭제
    // .noContent(): 상태 코드를 204 No Content로 설정
    // .build(): 위와 마찬가지로, 본문에 담을 데이터가 없으므로 상태 코드만 204로 설정한 후 ResponseEntity 객체를 완성하여 반환
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
