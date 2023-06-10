package com.blog.exception;

public class PostNotFound extends blogException {

    private static final String Message = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(Message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
