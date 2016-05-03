package com.estsoft.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/user" )
public class UserController {

	@RequestMapping( "/join" )
	public String join() {
		return "user/join";
	}
	@RequestMapping( "/login" )
	public String login() {
		return "user/login";
	}
	@RequestMapping( "/joinsuccess" )
	public String loginSuccess() {
		return "user/joinsuccess";
	}


}
