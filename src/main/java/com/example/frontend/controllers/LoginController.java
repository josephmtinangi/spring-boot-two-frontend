package com.example.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/login-page")
public class LoginController {

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}
}
