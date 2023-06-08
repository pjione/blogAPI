package com.blog.controller;

import com.blog.response.ErrorResponse;
import com.blog.response.ValidationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){

        List<ValidationDto> collect = e.getFieldErrors().stream()
                .map(v -> new ValidationDto(v.getField(), v.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse("400", "잘못된 요청입니다.", collect);
    }
}
