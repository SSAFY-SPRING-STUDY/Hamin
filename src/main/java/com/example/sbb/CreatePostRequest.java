package com.example.sbb;

public class CreatePostRequest {
    public String title;
    public String content;
    public String author;

    @Override
    public String toString() {
        return "CreatePostRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }


}
