package com.example.sbb.controller.dto;

import com.example.sbb.CreatePostRequest;
import com.example.sbb.service.PostResponse;
import com.example.sbb.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 생성
    @PostMapping("/api/posts")
    String createPost(@RequestBody CreatePostRequest request) {
        System.out.println(request);

        PostResponse response = postService.save(request);

        return "";
    }

}
