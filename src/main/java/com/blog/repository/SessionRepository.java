package com.blog.repository;

import com.blog.domain.Member;
import com.blog.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByMember(Member member);
    Optional<Session> findByAccessToken(String accessToken);
}
