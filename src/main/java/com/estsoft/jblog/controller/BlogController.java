package com.estsoft.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estsoft.jblog.annotation.Auth;

@Controller
@RequestMapping( "/blog" )
public class BlogController {
	
	@Auth
	@RequestMapping( "/" )
	public String root() {
		return "blog/blog-main";
	}
	@RequestMapping( "/admin" )
	public String admin() {
		return "blog/blog-admin-basic";
	}
	@RequestMapping( "/admin2" )
	public String admin2() {
		return "blog/blog-admin-category";
	}
	@RequestMapping( "/admin3" )
	public String admin3() {
		return "blog/blog-admin-write";
	}
}