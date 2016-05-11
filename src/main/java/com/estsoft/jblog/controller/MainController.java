package com.estsoft.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estsoft.jblog.service.BlogService;

@Controller
public class MainController {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping( "/" )
	public String root(Model model) {
		model.addAttribute("users", blogService.getAll());
		return "index";
	}
}