package com.cjsrhd94.boilerplate.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjsrhd94.boilerplate.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
