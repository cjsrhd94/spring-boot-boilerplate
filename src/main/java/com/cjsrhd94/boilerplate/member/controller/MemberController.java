package com.cjsrhd94.boilerplate.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.service.MemberCommand;
import com.cjsrhd94.boilerplate.member.service.MemberRead;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberCommand memberCommand;
	private final MemberRead memberRead;

	@PostMapping("/api/v1/member")
	public Long signUp(@RequestBody MemberDto.SignUp dto) {
		return memberCommand.signUp(dto);
	}

	@GetMapping("/api/v1/member/{id}")
	public MemberDto.Detail findMemberById(@PathVariable Long id) {
		return memberRead.findMemberById(id);
	}

	@PutMapping("/api/v1/member/{id}/profile")
	public Long updateProfile(@PathVariable Long id, MemberDto.Profile dto) {
		return memberCommand.updateProfile(id, dto);
	}
}
