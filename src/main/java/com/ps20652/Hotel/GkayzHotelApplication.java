package com.ps20652.Hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GkayzHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(GkayzHotelApplication.class, args);
	}
	
}
