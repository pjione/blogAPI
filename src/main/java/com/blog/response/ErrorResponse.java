package com.blog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Validation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final String status;
    private final String message;
    private final List<ValidationDto> validation;

    @Builder
    public ErrorResponse(String status, String message, List<ValidationDto> validation) {
        this.status = status;
        this.message = message;
        this.validation = validation;
    }
}