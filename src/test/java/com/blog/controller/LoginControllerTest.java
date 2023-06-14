package com.blog.controller;

import com.blog.domain.Member;
import com.blog.domain.Session;
import com.blog.repository.MemberRepository;
import com.blog.repository.SessionRepository;
import com.blog.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @BeforeEach
    void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("아이디 또는 비밀번호 불일치")
    void loginFail() throws Exception {

        Member member = Member.builder()
                .name("jiwon")
                .email("asdf@naver.com")
                .password("1234")
                .build();
        memberRepository.save(member);

        Login request = Login.builder()
                .email("1234@naver.com")
                .password("1234")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {

        Member member = Member.builder()
                .name("jiwon")
                .email("asdf@naver.com")
                .password("1234")
                .build();
        memberRepository.save(member);

        Login request = Login.builder()
                .email("asdf@naver.com")
                .password("1234")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("로그인 후 세션 생성")
    void session() throws Exception {

        Member member = Member.builder()
                .name("jiwon")
                .email("asdf@naver.com")
                .password("1234")
                .build();
        memberRepository.save(member);

        Login request = Login.builder()
                .email("asdf@naver.com")
                .password("1234")
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertThat(sessionRepository.findByMember(member).stream().count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속")
    void loginAuth() throws Exception {

        Member member = Member.builder()
                .name("jiwon")
                .email("asdf@naver.com")
                .password("1234")
                .build();
        memberRepository.save(member);

        Session session = Session.builder()
                .member(member)
                .build();
        sessionRepository.save(session);

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .cookie(new Cookie("SESSION", session.getAccessToken()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("로그인 후 검증되지 않은 세션값으로 권한이 필요한 페이지에 접속할 수 없다.")
    void loginAuth2() throws Exception {

        Member member = Member.builder()
                .name("jiwon")
                .email("asdf@naver.com")
                .password("1234")
                .build();
        memberRepository.save(member);

        Session session = Session.builder()
                .member(member)
                .build();
        sessionRepository.save(session);

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .header("Authorization", session.getAccessToken() + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }
}