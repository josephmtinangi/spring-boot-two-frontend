package com.example.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class DashboardController {

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index() {

		return "dashboard/index";
	}
}
