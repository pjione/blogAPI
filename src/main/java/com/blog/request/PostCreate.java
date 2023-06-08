package com.blog.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "{NotBlank.title}")
    private String title;
    @NotBlank(message = "{NotBlank.content")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
