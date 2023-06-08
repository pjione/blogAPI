package com.blog.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ValidationDto {
    private final String fieldName;
    private final String errorMessage;

}

