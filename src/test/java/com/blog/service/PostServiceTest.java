package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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
    @DisplayName("글 여러개 조회")
    void getList(){
        //given
        Post post1 = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        Post post2 = Post.builder()
                .title("제목입니다.2")
                .content("내용입니다.2")
                .build();
        /*Post save1 = postRepository.save(post1);
        Post save2 = postRepository.save(post2);*/

        postRepository.saveAll(List.of(post1,post2));

        //when
        List<PostResponse> list = postService.getList();
        //then
        assertNotNull(list);
        assertThat(list.size()).isEqualTo(2L);

    }

}