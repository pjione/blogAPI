package com.blog.controller;


import com.blog.config.data.MemberSession;
import com.blog.domain.Member;
import com.blog.exception.InvalidLoginInformation;
import com.blog.exception.InvalidRequest;
import com.blog.repository.MemberRepository;
import com.blog.request.Login;
import com.blog.request.SignUp;
import com.blog.response.SessionResponse;
import com.blog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public String test(MemberSession memberSession){
        return memberSession.getEmail();
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated Login request){
        String accessToken = loginService.login(request);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost") // todo 서버 환경에 따른 수정 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();

    }
    @PostMapping("/signUp")
    public void signup(@RequestBody @Validated SignUp signUp){
        loginService.signUp(signUp);
    }
}
