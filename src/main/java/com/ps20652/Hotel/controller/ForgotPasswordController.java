package com.ps20652.Hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    @GetMapping
	public String register() {
		return "security/forgot-password";
	}
}
