package br.com.rodrigoamora.eventosti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping("/")
	public String aaa() {
		return "index.html";
	}
	
}
