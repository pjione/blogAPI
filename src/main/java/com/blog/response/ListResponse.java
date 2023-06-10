package com.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ListResponse<T> {
    private T data;
}
