package com.cjsrhd94.boilerplate.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.repository.MemberQuery;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRead {
	private final MemberQuery memberQuery;
	public MemberDto.Detail findMemberById(Long id) {
		return new MemberDto.Detail(memberQuery.findMemberById(id));
	}
}
