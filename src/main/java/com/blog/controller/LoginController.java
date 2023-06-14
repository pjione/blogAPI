package com.blog.controller;


import com.blog.config.data.MemberSession;
import com.blog.domain.Member;
import com.blog.exception.InvalidLoginInformation;
import com.blog.exception.InvalidRequest;
import com.blog.repository.MemberRepository;
import com.blog.request.Login;
import com.blog.response.SessionResponse;
import com.blog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
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
    public SessionResponse login(@RequestBody Login request, HttpServletRequest httpServletRequest){
        return new SessionResponse(loginService.login(request));
    }
}
