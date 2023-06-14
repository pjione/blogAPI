package com.blog.service;

import com.blog.domain.Member;
import com.blog.domain.Session;
import com.blog.exception.AlreadyExistsEmailException;
import com.blog.repository.MemberRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.SessionRepository;
import com.blog.request.Login;
import com.blog.request.SignUp;
import org.apache.juli.logging.Log;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp(){
        SignUp signUp = SignUp.builder()
                .name("jiwon")
                .email("jiwon@naver.com")
                .password("1234")
                .build();
        loginService.signUp(signUp);

        assertThat(memberRepository.count()).isEqualTo(1);

        List<Member> list = memberRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getEmail()).isEqualTo("jiwon@naver.com");
    }

    @Test
    @DisplayName("회원가입 이메일 중복")
    void signUpFail(){

        memberRepository.save(Member.builder()
                .email("jiwon@naver.com")
                .name("haha")
                .password("1234")
                .build());

        SignUp signUp = SignUp.builder()
                .name("jiwon")
                .email("jiwon@naver.com")
                .password("1234")
                .build();

        assertThatThrownBy(() ->  loginService.signUp(signUp)).isInstanceOf(AlreadyExistsEmailException.class);
    }


    @Test
    @DisplayName("로그인 성공 후 세션생성")
    void loginSuccess(){
        Member member = memberRepository.save(Member.builder()
                .name("jiwon")
                .email("jiwon@naver.com")
                .password("1234")
                .build());

        Login request = Login.builder()
                .email("jiwon@naver.com")
                .password("1234")
                .build();
        loginService.login(request);

        assertThat(sessionRepository.findByMember(member).stream().count()).isEqualTo(1L);

    }
}
