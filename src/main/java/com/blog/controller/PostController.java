package com.blog.controller;

import com.blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {
    @PostMapping("/posts")
    public Map<Object, Object> post(@RequestBody @Validated PostCreate postCreate, BindingResult result){

        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();

            HashMap<Object, Object> error = new HashMap<>();
            error.put(field,defaultMessage);

            return error;
        }

        return Map.of();
    }
}
