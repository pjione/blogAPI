package com.blog.controller;

import com.blog.request.PostCreate;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @PostMapping("/posts")
    public String post(@RequestBody PostCreate postCreate){
        return "hello world";
    }
}
