package com.blog.exception;

import com.blog.response.Validation;
import lombok.Getter;

public class InvalidRequest extends blogException{

    private static final String Message = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(Message);
    }

    public InvalidRequest(String fieldName, String errorMessage){
        super(Message);
        addValidation(fieldName, errorMessage);
    }
    @Override
    public int getStatusCode(){
        return 400;
    }
}
