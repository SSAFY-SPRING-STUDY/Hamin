package com.sbb.domain.post.controller.dto;

import com.sbb.domain.member.entity.MemberEntity;
import com.sbb.domain.post.entity.PostEntity;

public record PostRequest(
        String title,
        String content
) {
    // day3까지는 요청 body에서 author를 받았을 수 있다.
    // 하지만 day4부터는 작성자를 클라이언트가 보내면 안 된다.
    // 작성자는 Authorization 토큰을 통해 서버가 직접 판단해야 한다.
    public PostEntity toEntity(MemberEntity author) {
        return PostEntity.create(
                title,
                content,
                author
        );
    }
}