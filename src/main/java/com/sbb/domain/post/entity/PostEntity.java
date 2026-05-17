package com.sbb.domain.post.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEntity {
    private Long id;
    private String title;
    private String content;
    private String author;
}
