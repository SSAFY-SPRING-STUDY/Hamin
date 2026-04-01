package com.sbb.repository;

// Repository의 필요성: 컨트롤러나 서비스가 직접 리스트에 접근하면 역할이 섞여서 구조가 지저분해지기 때문
// -> 저장 관련 책임은 전부 Repository

import com.sbb.entity.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    // 게시글들을 메모리에 담아둘 리스트
    // static을 사용하는 이유: 이번 과제는 DB가 없기 때문에 프로그램이 실행되는 동안 데이터가 유지되도록 하기 위함
    // 즉, 객체가 새로 만들어져도 저장 리스트는 공통으로 유지
    private static final List<PostEntity> posts = new ArrayList<>();
    // ID 자동 증가용 sequence
//    private static Long sequence = 1L; Repository가 아닌 Entity에 책임 부여?

    // TODO: 전달받은 entity에 id 부여 후 리스트에 추가, 저장된 entity 반환
    // Repository는 저장소를 직접 다루는 계층이므로, 보통 Entity 기준으로 반환
    // 저장 결과가 반영된 엔티티를 반환해야 하므로 반환형은 PostEntity
    public PostEntity save(PostEntity entity) {
//        entity.setId(sequence++);
        posts.add(entity);
        return entity;
    }

    // TODO: 저장된 모든 게시글 반환
    // 여러 게시글을 반환해야 하므로 반환형은 List<PostEntity>
    public List<PostEntity> findAll() {
        return posts;
        // new ArrayList<>(posts); 로 반환하면 원본 리스트를 직접 밖에 넘기지 않아서 더 안전
    }

    // TODO: 리스트에서 id가 같은 게시글 하나 찾기, 있으면 반환, 없으면 없으면 빈 Optional 반환
    // Optional? : “값이 있을 수도 있고, 없을 수도 있는” 객체를 감싸는 컨테이너.
    // 값이 있으면 객체를 담아서 반환, 없으면 비어 있는 상태로 반환
    // Optional 반환 이유: 조회 결과가 없을 수도 있기 때문. 게시글이 없으면 null 대신 Optional.empty()로 표현
    // -> “없을 수 있음”을 코드로 명확히 표현 가능 + Service에서 예외 처리하기 쉬움 (NullPointerException 방지?)
    // 만약 PostEntity를 바로 반환하면, 값이 없을 때 null 처리를 직접 해야 해서 불편
    public Optional<PostEntity> findById(Long id) {
        for (PostEntity post : posts) {
            if (post.getId().equals(id)) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    // stream이란? : 데이터(리스트 등)를 더 선언적이고 편하게 처리하는 방식
    // for문으로 하나하나 직접 돌리는 대신 “찾아라”, “걸러라”, “변환해라” 같은 작업을 더 간결하게 표현하는 방법
    // findById(Long id)를 stream으로 구현하면?
//    public Optional<PostEntity> findById(Long id) {
//        return posts.stream() // posts 리스트를 stream 형태로 바꾸기. "리스트를 연속적으로 처리하겠다"는 시작점
//                .filter(post -> post.getId().equals(id))  // 조건에 맞는 것만 남기기
//                .findFirst(); // 조건을 통과한 첫 번째 요소를 찾기. 반환형은 자동으로 Optional<PostEntity>
//    }

    // TODO: 해당 id를 가진 게시글을 리스트에서 제거
    // removeIf: 조건에 맞는 요소를 리스트에서 삭제하는 메서드. for문으로 삭제하는것보다 안전하고 간단
    // posts를 하나씩 보면서 조건이 true인 요소는 제거
    // 삭제는 보통 특별히 돌려줄 데이터가 없다면 void 반환형
    public void deleteById(Long id) {
        // post -> post.getId().equals(id) 는 람다식
        // post 하나를 받아서 그 post의 id가 전달받은 id와 같으면 true를 반환하겠다는 의미
        posts.removeIf(post -> post.getId().equals(id));
    }
}
