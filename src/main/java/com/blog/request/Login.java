package com.blog.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class Login {

    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Builder
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
