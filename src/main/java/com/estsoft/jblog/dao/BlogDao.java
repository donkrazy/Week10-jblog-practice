package com.estsoft.jblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.BlogVo;
import com.estsoft.jblog.vo.UserVo;

@Repository
public class BlogDao {
	@Autowired
	private SqlSession sqlSession;
	
	public BlogVo get( String name ) {
		return sqlSession.selectOne("blog.selectByUserName", name );
	}
	
	public int insert( UserVo userVo ){
		String name = userVo.getName();
		BlogVo blogVo = new BlogVo();
		blogVo.setName(name);
		sqlSession.insert("blog.insert", blogVo);
		return blogVo.getId();
	}
}
