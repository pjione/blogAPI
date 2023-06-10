package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public void write(PostCreate postCreate){
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);
    }
    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getListDsl(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        post.edit(postEdit);

        return new PostResponse(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        postRepository.delete(post);
    }
}



