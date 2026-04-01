package com.sbb.service;

// Service 계층의 역할
// - Request DTO → Entity 변환
// - Repository 호출 및 저장
// - Entity → Response DTO 변환
// - 게시글이 없을 때 예외 처리
// - 수정/삭제 로직 수행

import com.sbb.controller.dto.request.PostRequest;
import com.sbb.controller.dto.response.PostResponse;
import com.sbb.entity.PostEntity;
import com.sbb.exception.PostNotFoundException;
import com.sbb.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
// @RequiredArgsConstructor: final 필드인 postRepository를 위한 생성자 생성(lombok)
@Service
public class PostService {
    // PostRepository를 사용해야 하므로 Repository를 주입
    private final PostRepository postRepository;

    // TODO: Request DTO를 Entity로 변환 -> Repository에 저장 -> 저장된 Entity를 Response DTO로 변환 후 반환
    public PostResponse save(PostRequest request) {
        PostEntity entity = toEntity(request);
        PostEntity savedEntity = postRepository.save(entity);
        return toResponse(savedEntity);
    }

    // TODO: Repository에서 전체 Entity 목록 조회 -> 각각을 PostResponse로 변환 후 리스트로 반환
    // 게시글 전체 목록 조회이므로 여러 개, 즉 list 반환
    // Repository에서는 List<PostEntity>를 받겠지만 Service에서는 List<PostResponse>로 변환
    public List<PostResponse> findAll() {
        List<PostEntity> entities = postRepository.findAll();
        List<PostResponse> responses = new ArrayList<>();

        for (PostEntity entity : entities) {
            responses.add(toResponse(entity));
        }

        return responses;
    }
    // stream으로 구현한 findAll()
//    public List<PostResponse> findAll() {
//        return postRepository.findAll().stream()
//                .map(this::toResponse)
//                .toList();
//    }

    // TODO: Repository에서 Optional<PostEntity> 조회 -> 값이 없으면 예외 발생, 있으면 `PostResponse`로 변환 후 반환
    // 단건 조회이므로 Response DTO 하나만 반환
    // Repository에서는 `Optional<PostEntity>`를 반환했지만
    // Service에서는 보통 없으면 예외를 던지고 있으면 `PostResponse`를 반환
    // .orElseThrow?: Optional과 함께 사용하는 예외처리 방식
    // - Optional 안에 값이 있으면 그 값을 꺼내고, 없으면 예외를 던지기
    // RuntimeException?: 프로그램 실행 중 발생하는 예외의 대표적인 종류, 클래스
    // 왜 Repository가 아닌 Service에서 예외를 처리하는지?
    // - Repository는 저장소 역할만 하면 되므로 Optional만 반환
    // - 반면 Service는 비즈니스 로직 계층이므로 못 찾은 상황을 어떻게 처리할지 결정해야 함
    public PostResponse findById(Long id) {
        PostEntity entity = postRepository.findById(id)
                // findById(id) 결과에 게시글이 들어 있으면 → PostEntity 꺼냄
                // 비어 있으면 → RuntimeException 발생, RuntimeException 타입의 예외 객체 생성
                // "() ->" 는 "입력값 없이 뭔가를 실행, 생성"하라는 뜻
//                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id=" + id));

                // 404 Not Found를 반환하기 위해 PostNotFoundException 클래스 사용
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다. id=" + id));
        return toResponse(entity);
    }

    // TODO: Repository에서 게시글 찾기 -> 없으면 예외 발생, 있으면 제목/내용/작성자 수정
    // 실무에서는 수정 후 수정된 결과를 다시 반환하기도 하지만, 이번 과제에서는 void
    // DB가 아니고 메모리 리스트 안 객체를 직접 수정하는 구조라서 객체 entity의 필드를 수정하면 리스트 안의 값도 수정됨
    // -> 따로 save하지 않아도 됨
    public void update(Long id, PostRequest request) {
        PostEntity entity = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id=" + id));
                // 404 Not Found
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다. id=" + id));

        entity.update(request.getTitle(), request.getContent(), request.getAuthor());
    }

    // TODO: 먼저 id가 존재하는지 확인 -> 없으면 예외 발생, 있으면 삭제 수행
    // id가 존재하는지 먼저 확인하는 이유: 없는 id에 대해 404 처리를 하기 위해서
    public void delete(Long id) {
        postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id=" + id));
                // 404 Not Found
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다. id=" + id));
        postRepository.deleteById(id);
    }

    // DTO 변환용 private 메서드
    // Service 안에서는 DTO ↔ Entity 변환이 자주 필요하므로 보통 private 메서드로 분리
    // PostResponse, PostRequest 모두 생성자 기반이므로 반환값으로 생성자를 만들기
    // TODO: request의 title/content/author를 꺼내서 새 PostEntity에 넣기
    private PostEntity toEntity(PostRequest request) {
        return new PostEntity(
                request.getTitle(),
                request.getContent(),
                request.getAuthor()
        );
    }

    // TODO: entity의 id/title/content/author를 꺼내서 새 PostResponse에 넣기
    private PostResponse toResponse(PostEntity entity) {
        return new PostResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor()
        );
    }
}
