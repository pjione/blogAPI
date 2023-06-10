package com.blog.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostEdit {
    @NotBlank(message = "{NotBlank.title}")
    private final String title;
    @NotBlank(message = "{NotBlank.content}")
    private final String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
