package com.yin4learning.restful_web_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RestfulController")
public class restfulWebServiceController {

	@GetMapping("/Test")
	public String getTest() {
		return "test";
	}
	
	@PostMapping("/postTest")
	public String getStringTest() {
		return "testerwrw";
	}
}
