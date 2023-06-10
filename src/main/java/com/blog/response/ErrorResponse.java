package com.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    private final String status;
    private final String message;
    private final List<Validation> validations;
    private final Validation validation;

    @Builder
    public ErrorResponse(String status, String message, List<Validation> validations, Validation validation) {
        this.status = status;
        this.message = message;
        this.validations = validations == null ? new ArrayList<>() : validations;
        this.validation = validation;
    }
}