package com.blog.config;

import com.blog.config.data.MemberSession;
import com.blog.domain.Session;
import com.blog.exception.Unauthorized;
import com.blog.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if(servletRequest == null){
            throw new Unauthorized();
        }
        Cookie[] cookies = servletRequest.getCookies();
        if(cookies.length==0){
            throw new Unauthorized();
        }
        String accessToken = cookies[0].getValue();

        Session session = sessionRepository.findByAccessToken(accessToken).orElseThrow(Unauthorized::new);

        return new MemberSession(session.getMember().getEmail());
    }
}
