package com.blog.exception;

public class InvalidLoginInformation extends blogException{
    public InvalidLoginInformation() {
        super("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    @Override
    public int getStatusCode() {
        return 401;
    }
}
