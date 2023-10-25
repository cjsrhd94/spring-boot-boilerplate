package com.cjsrhd94.boilerplate.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/hello")
	public String sayHelloWorld() {
		return "hello world!";
	}
}
