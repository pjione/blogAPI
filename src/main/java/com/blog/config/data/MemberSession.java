package com.blog.config.data;

import lombok.Getter;

@Getter
public class MemberSession {
    private final String email;

    public MemberSession(String email) {
        this.email = email;
    }
}
