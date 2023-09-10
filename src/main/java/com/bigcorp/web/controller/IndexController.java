package com.bigcorp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping({ "/", "/logout" })
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/basic-protected")
	public String basicProtected() {
		return "basic-protected";
	}

	@GetMapping("/page")
	public String page() {
		return "page";
	}

}