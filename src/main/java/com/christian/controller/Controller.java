package com.christian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/")
	public String display() {
		return "Welcome to the E-commerce application!";
	}
	
}
