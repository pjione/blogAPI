package com.blog.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
