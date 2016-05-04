package com.estsoft.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.jblog.service.UserService;
import com.estsoft.jblog.vo.UserVo;

public class AuthJoinInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired()
	private UserService userService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if ( "GET".equals( request.getMethod() )){
			return;
		}
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		UserVo userVo = new UserVo();
		userVo.setName(name);
		userVo.setPassword(password);
		UserVo authUser = userService.login(userVo);
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser );
	}

}
