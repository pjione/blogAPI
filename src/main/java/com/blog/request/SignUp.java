package com.blog.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SignUp {
    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @Builder
    public SignUp(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
