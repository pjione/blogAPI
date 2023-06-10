package com.blog.exception;

public class PostNotFound extends RuntimeException {

    public PostNotFound() {
        super( "존재하지 않는 글입니다.");
    }
}
