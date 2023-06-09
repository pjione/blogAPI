package com.blog.repository;

import com.blog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long > {
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Member> findByEmail(String email);
}
