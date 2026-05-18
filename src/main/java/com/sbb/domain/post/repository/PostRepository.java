package com.sbb.domain.post.repository;

import com.sbb.domain.post.entity.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepository {

    // 현재 단계에서는 DB 대신 Map을 게시글 저장소처럼 사용한다.
    // Key는 게시글 id, Value는 게시글 객체이다.
    private static final Map<Long, PostEntity> postStore = new ConcurrentHashMap<>();

    public PostEntity save(PostEntity post) {
        postStore.put(post.getId(), post);

        return post;
    }

    public List<PostEntity> findAll() {
        return new ArrayList<>(postStore.values());
    }

    public Optional<PostEntity> findById(Long id) {
        return Optional.ofNullable(postStore.get(id));
    }

    public void delete(PostEntity post) {
        // id만 받아서 삭제할 수도 있지만,
        // 명세에서는 delete(PostEntity post) 형태를 힌트로 제시하고 있다.
        postStore.remove(post.getId());
    }
}