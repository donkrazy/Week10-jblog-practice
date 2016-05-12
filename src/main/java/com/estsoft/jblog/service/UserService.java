package com.estsoft.jblog.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.jblog.dao.BlogDao;
import com.estsoft.jblog.dao.CategoryDao;
import com.estsoft.jblog.dao.UserDao;
import com.estsoft.jblog.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private BlogDao blogDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CategoryDao categoryDao;
	
	public void join( UserVo vo ) {
		userDao.insert(vo);
		// 자동 블로그 생성
		int blogId = blogDao.insert(vo);
		categoryDao.insert(blogId);
	}
	
	public Map<String, Object> checkEmail(UserVo vo, String name){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo == null );
		return map;
	}
	
	public UserVo login( UserVo vo ) {
		UserVo authUser = userDao.get( vo );
		return authUser;
	}
	
	public UserVo getUser( String name ) {
		UserVo vo = userDao.get( name );
		return vo;
	}
	
	public String checkId(String name){
		int deleted = userDao.checkId(name);
		if(deleted!=0){
			return "duplicated";
		}
		return "success";
	}
}
