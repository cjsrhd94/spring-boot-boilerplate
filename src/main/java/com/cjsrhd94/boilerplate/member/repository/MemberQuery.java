package com.cjsrhd94.boilerplate.member.repository;

import static com.cjsrhd94.boilerplate.member.entity.QMember.*;

import org.springframework.stereotype.Repository;

import com.cjsrhd94.boilerplate.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQuery {
	private final JPAQueryFactory queryFactory;

	public Member findMemberById(Long id) {
		return queryFactory
			.selectFrom(member)
			.where(member.id.eq(id))
			.fetchOne();
	}

	public Member findMemberByUsername(String username) {
		return queryFactory
			.selectFrom(member)
			.where(member.username.eq(username))
			.fetchOne();
	}
}
