package com.blog.request;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
public class PostCreate {

    @NotBlank(message = "{NotBlank.title}")
    private final String title;
    @NotBlank(message = "{NotBlank.content}")
    private final String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
