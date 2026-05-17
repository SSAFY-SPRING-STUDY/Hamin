package com.sbb.domain.post.controller.dto;

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
                MemberResponse.from(post.getAuthor())
        );
    }
}