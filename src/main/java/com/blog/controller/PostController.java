package com.blog.controller;

import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.ListResponse;
import com.blog.response.PostResponse;
import com.blog.service.PostService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping("/posts")
    public void post(@RequestBody @Validated PostCreate request) {
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
       return postService.get(postId);
    }

    //@GetMapping("/posts")
    public ListResponse<List<PostResponse>> getList(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<PostResponse> list = postService.getList(pageable);
        return new ListResponse<>(list);
    }
    @GetMapping("/posts") //querydsl 페이징
    public ListResponse<List<PostResponse>> getList(PostSearch postSearch){
        List<PostResponse> list = postService.getListDsl(postSearch);
        return new ListResponse<>(list);
    }
    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Validated PostEdit request){
        return postService.edit(postId, request);
    }
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
