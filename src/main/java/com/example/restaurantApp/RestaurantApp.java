package com.example.restaurantApp;

import
		org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class RestaurantApp {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApp.class, args);
	}
	@GetMapping("muie")
	public String muie()
	{
		return "muie steaua";
	}


}
