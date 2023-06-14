package com.blog.service;

import com.blog.domain.Member;
import com.blog.domain.Session;
import com.blog.exception.InvalidLoginInformation;
import com.blog.repository.MemberRepository;
import com.blog.repository.SessionRepository;
import com.blog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    @Transactional
    public String login(Login request){
        Member member = memberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(InvalidLoginInformation::new);
        Session session = Session.builder()
                .member(member)
                .build();
        sessionRepository.save(session);
        return session.getAccessToken();
    }
}
