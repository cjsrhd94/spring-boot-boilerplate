package com.cjsrhd94.boilerplate.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.service.MemberCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberCommand memberCommand;

	@PostMapping("/api/v1/member")
	public Long signUp(@RequestBody MemberDto.SignUp memberDto) {
		return memberCommand.signUp(memberDto);
	}
}
