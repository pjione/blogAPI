package com.blog.controller;

import com.blog.request.PostCreate;
import com.blog.response.ListResponse;
import com.blog.response.PostResponse;
import com.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/posts")
    public void post (@RequestBody @Validated PostCreate request) {
        postService.write(request);
    }
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
       return postService.get(postId);
    }
    @GetMapping("/posts")
    public ListResponse<List<PostResponse>> getList(){
        List<PostResponse> list = postService.getList();
        return new ListResponse<>(list);
    }
}
