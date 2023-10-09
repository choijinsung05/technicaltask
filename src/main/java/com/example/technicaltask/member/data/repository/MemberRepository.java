package com.example.technicaltask.member.data.repository;

import com.example.technicaltask.member.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String email);
    boolean existsByMemberId(String email);
}
