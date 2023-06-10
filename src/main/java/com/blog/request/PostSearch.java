package com.blog.request;

import lombok.*;

import javax.annotation.PostConstruct;

@Getter
public class PostSearch {

    private final Integer page;
    private final int size = 10;

    @Builder
    public PostSearch(Integer page) {
        this.page = page == null ? 1 : page;
    }

    public int getOffset(){
        return (Math.max(1, page) - 1) * size;
    }

}
