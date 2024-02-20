package com.ps20652.Hotel.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/verify")
public class connectAccountController {
    @GetMapping
	public String login(Authentication authentication, Model model) {
	    if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
            // Lấy userID của người dùng đã đăng nhập
            // int userId = getUserIDByUsername(username);
        }
		return "security/connectAccount";
	}
}
