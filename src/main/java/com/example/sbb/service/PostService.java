package com.example.sbb.service;

import com.example.sbb.CreatePostRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepositry postRepositry;

    public PostService(PostRepositry postRepositry) {
        this.postRepositry = postRepositry;
    }

    public postResponse save(CreatePostRequest request){
        postRepositry.save
    }
}
