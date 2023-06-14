package com.blog.service;

import com.blog.crypto.PasswordEncoder;
import com.blog.domain.Member;
import com.blog.domain.Session;
import com.blog.exception.AlreadyExistsEmailException;
import com.blog.exception.InvalidLoginInformation;
import com.blog.exception.InvalidRequest;
import com.blog.repository.MemberRepository;
import com.blog.repository.SessionRepository;
import com.blog.request.Login;
import com.blog.request.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;
    @Transactional
    public String login(Login request){
       /* Member member = memberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(InvalidLoginInformation::new);*/
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(InvalidLoginInformation::new);

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        boolean matches = passwordEncoder.matches(request.getPassword(), member.getPassword());

        if(!matches){
            throw new InvalidLoginInformation();
        }
        Session session = Session.builder()
                .member(member)
                .build();
        sessionRepository.save(session);
        return session.getAccessToken();
    }
    @Transactional
    public void signUp(SignUp signUp) {

        Optional<Member> findMember = memberRepository.findByEmail(signUp.getEmail());

        if(findMember.isPresent()){
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encryptedPassword = passwordEncoder.encrypt(signUp.getPassword());

        Member member = Member.builder()
                .email(signUp.getEmail())
                .password(encryptedPassword)
                .name(signUp.getName())
                .build();
        memberRepository.save(member);
    }
}
