package com.sbb.domain.post.controller.dto.request;

import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.post.entity.PostEntity;

public record PostRequest(
        String title,
        String content
) {
    public PostEntity toEntity(MemberEntity author) {
        return PostEntity.create(
                title,
                content,
                author
        );
    }
}