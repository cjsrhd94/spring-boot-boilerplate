package com.cjsrhd94.boilerplate.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRead {
	private final MemberRepository memberRepository;
	public MemberDto.Detail findMemberById(Long id) {
		return new MemberDto.Detail(memberRepository.getReferenceById(id));
	}
}
