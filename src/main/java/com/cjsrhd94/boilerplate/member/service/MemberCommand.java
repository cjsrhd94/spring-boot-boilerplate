package com.cjsrhd94.boilerplate.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommand {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	public Long signUp(MemberDto.SignUp dto) {
		return memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())))
			.getId();
	}
}
