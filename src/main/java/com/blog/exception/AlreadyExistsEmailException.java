package com.blog.exception;

public class AlreadyExistsEmailException extends blogException{

    public AlreadyExistsEmailException() {
        super("이미 가입된 이메일입니다.");
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
