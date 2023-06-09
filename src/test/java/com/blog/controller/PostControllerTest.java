package com.blog.controller;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private ObjectMapper objectMapper;  //  objectMapper.writeValueAsString(postCreate); <- 객체에 기본생성자 없을시 에러발생
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 hello world를 출력한다.")
    void test() throws Exception {

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcResultHandlers.print());//컨트롤러 요청 내용출력

    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다")
    void test2() throws Exception {

        PostCreate postCreate = PostCreate.builder()
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation[0].fieldName").value("title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation[0].errorMessage").value("제목을 입력해주세요."))
                //.andExpect(MockMvcResultMatchers.content().string("hello"))
                .andDo(MockMvcResultHandlers.print());//컨트롤러 요청 내용출력

    }

    @Test
    @DisplayName("/posts 요청시 db에 값이 저장된다.")
    void test3() throws Exception {

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.content().string("hello"))
                .andDo(MockMvcResultHandlers.print());//컨트롤러 요청 내용출력

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat("제목입니다.").isEqualTo(post.getTitle());
        assertThat("내용입니다.").isEqualTo(post.getContent());

    }
    @Test
    @DisplayName("글 1개 조회")
    void findOne() throws Exception {
        //given
       Post post = Post.builder()
               .title("titleaaaaaaaaaaaa")
               .content("content")
               .build();
       postRepository.save(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleaaaaa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(post.getContent()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void getList() throws Exception {
        //given
        List<Post> list = IntStream.range(0,30)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(list);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", Matchers.is(30)))
                .andDo(MockMvcResultHandlers.print());

    }
}