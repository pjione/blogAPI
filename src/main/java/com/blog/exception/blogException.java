package com.blog.exception;

import com.blog.response.Validation;
import lombok.Getter;

@Getter
public abstract class blogException extends RuntimeException{
    private Validation validation;
    public blogException(String message) {
        super(message);
    }
    public blogException(String message, Throwable cause) {
        super(message, cause);
    }
    public abstract int getStatusCode();

    public void addValidation(String fieldName, String errorMessage){
       validation = new Validation(fieldName, errorMessage);
    }
}
