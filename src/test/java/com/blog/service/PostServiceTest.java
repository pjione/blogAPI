package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }
    @Test
    @DisplayName("글 작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postService.write(postCreate);

        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목입니다.");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
        
    }

    @Test
    @DisplayName("글 한개 조회")
    void test2(){
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        Post save = postRepository.save(post);

        //when
        PostResponse postResponse = postService.get(save.getId());

        //then
        assertNotNull(postResponse);
        assertThat(postResponse.getId()).isEqualTo(save.getId());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void getList(){
        //given
        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PageRequest pageRequest = PageRequest.of(0,5, Sort.Direction.DESC, "id");

        //when
        List<PostResponse> list = postService.getList(pageRequest);
        //then
        assertNotNull(list);
        assertThat(list.size()).isEqualTo(5);

    }
    @Test
    @DisplayName("글 1페이지 조회 - 쿼리dsl적용")
    void getListDsl(){
        //given
        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(2)
                .build();

        //when
        List<PostResponse> list = postService.getListDsl(postSearch);
        //then
        assertNotNull(list);
        assertThat(list.size()).isEqualTo(10);
        assertThat(list.get(0).getTitle()).isEqualTo("제목19");

    }
    @Test
    @DisplayName("글 수정")
    void updatePost(){
        //given
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("제목변경")
                .content("내용입니다.")
                .build();
        //when
       postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다."));
        
        assertThat(changedPost.getTitle()).isEqualTo("제목변경");
        assertThat(changedPost.getContent()).isEqualTo("내용입니다.");

    }
    @Test
    @DisplayName("글 삭제")
    void deletePost(){
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        postRepository.save(post);

        //when
        postService.delete(post.getId());

        assertEquals(0, postRepository.count());
    }

}