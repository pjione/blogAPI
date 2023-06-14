package com.blog.service;

import com.blog.domain.Member;
import com.blog.domain.Session;
import com.blog.repository.MemberRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.SessionRepository;
import com.blog.request.Login;
import org.apache.juli.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MemberRepository memberRepository;
    private Member member;
    @BeforeEach
    void memberSet(){
       member = memberRepository.save(Member.builder()
            .name("jiwon")
            .email("jiwon@naver.com")
            .password("1234")
            .build());
    }
    @Test
    @DisplayName("로그인 성공 후 세션생성")
    void loginSuccess(){
        Login request = Login.builder()
                .email("jiwon@naver.com")
                .password("1234")
                .build();
        loginService.login(request);

        Assertions.assertThat(sessionRepository.findByMember(member).stream().count()).isEqualTo(1L);

    }
}
