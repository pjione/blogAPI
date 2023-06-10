package com.blog.controller;

import com.blog.exception.blogException;
import com.blog.response.ErrorResponse;
import com.blog.response.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){

        List<Validation> collect = e.getFieldErrors().stream()
                .map(v -> new Validation(v.getField(), v.getDefaultMessage()))
                .collect(Collectors.toList());

        if(collect.size()>1){
            return ErrorResponse.builder()
                    .status("400")
                    .message("잘못된 요청입니다.")
                    .validations(collect)
                    .build();
        }
        return ErrorResponse.builder()
                .status("400")
                .message("잘못된 요청입니다.")
                .validation(collect.get(0))
                .build();


    }

    @ExceptionHandler(blogException.class)
    public ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(blogException e){

        int statusCode = e.getStatusCode();
        ErrorResponse response = ErrorResponse.builder()
                .status(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();


        return ResponseEntity.status(statusCode)
                .body(response);
    }
}
