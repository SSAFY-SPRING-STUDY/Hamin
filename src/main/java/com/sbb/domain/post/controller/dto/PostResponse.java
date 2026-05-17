package com.sbb.domain.post.controller.dto.response;

import com.sbb.domain.member.controller.dto.MemberResponse;
import com.sbb.domain.post.entity.PostEntity;

public record PostResponse(
        Long id,
        String title,
        String content,
        MemberResponse memberResponse
) {
    public static PostResponse from(PostEntity post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),

                // PostEntity의 author는 MemberEntity이므로
                // 그대로 노출하지 않고 MemberResponse로 변환해서 응답한다.
                MemberResponse.from(post.getAuthor())
        );
    }
}