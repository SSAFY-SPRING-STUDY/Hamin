package com.sbb.domain.post.service;

import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.member.repository.MemberRepository;
import com.sbb.domain.post.controller.dto.PostRequest;
import com.sbb.domain.post.controller.dto.PostResponse;
import com.sbb.domain.post.entity.PostEntity;
import com.sbb.domain.post.repository.PostRepository;
import com.sbb.global.exception.CustomException;
import com.sbb.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse create(PostRequest request, Long authorId) {
        // Controller에서 토큰을 통해 authorId를 구해오고,
        // Service에서는 그 id에 해당하는 회원이 실제로 존재하는지 확인한다.
        MemberEntity author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        PostEntity post = request.toEntity(author);
        PostEntity savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return PostResponse.from(post);
    }

    public PostResponse update(PostRequest request, Long id, Long authorId) {
        // 권한 검증 로직 1.
        // 로그인한 사용자가 실제 존재하는 회원인지 확인
        MemberEntity author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 권한 검증 로직 2.
        // 수정하려는 게시글이 존재하는지 확인
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 권한 검증 로직 3.
        // 게시글 작성자 id와 현재 로그인한 사용자 id가 같은지 확인
        if (!post.getAuthor().getId().equals(author.getId())) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        post.update(request.title(), request.content());

        return PostResponse.from(post);
    }

    public void delete(Long id, Long authorId) {
        // 권한 검증 로직 1.
        // 로그인한 사용자가 실제 존재하는 회원인지 확인
        MemberEntity author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 권한 검증 로직 2.
        // 삭제하려는 게시글이 존재하는지 확인
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 권한 검증 로직 3.
        // 게시글 작성자 id와 현재 로그인한 사용자 id가 같은지 확인
        if (!post.getAuthor().getId().equals(author.getId())) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        postRepository.delete(post);
    }
}