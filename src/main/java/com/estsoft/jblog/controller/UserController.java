package com.estsoft.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.jblog.annotation.Auth;
import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.UserVo;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping( value="/join", method=RequestMethod.GET )
	public String join() {
		return "user/join";
	}
	
	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
			model.addAllAttributes( result.getModel() );
			return "/user/join";
		}
		userService.join(vo);
		return "user/joinsuccess";
	}
	
	@RequestMapping( value="/login")
	public String login() {
		return "user/login";
	}
	
	@Auth
	@RequestMapping( "/joinsuccess" )
	public String loginSuccess() {
		return "user/joinsuccess";
	}
	
	@Auth
	@RequestMapping( value="/modify", method=RequestMethod.GET )
	public String modify() {
		return "user/modify";
	}
	
	@ResponseBody
	@RequestMapping("/check-id")
	public String idCheck(@RequestParam String name){
		return userService.checkId(name);
	}
}
