package com.ps20652.Hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
	public String admin() {
	
		return "admin/Dashboard";
	}


	@GetMapping("/room")
	public String admin1() {
	
		return "admin/room";
	}


	@GetMapping("/type-room")
	public String admin2() {
	
		return "admin/type-room";
	}


	@GetMapping("/foodService")
	public String admin3() {
	
		return "admin/foodService";
	}


	@GetMapping("/drinkService")
	public String admin4() {
	
		return "admin/drinkService";
	}


	@GetMapping("/booking")
	public String admin5() {
	
		return "admin/booking";
	}
}