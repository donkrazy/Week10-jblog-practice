package com.estsoft.jblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	
	public UserVo get( String name ) {
		return sqlSession.selectOne("user.selectByName", name );
	}
	
	public UserVo get( int userId ) {
		return sqlSession.selectOne("user.selectById", userId);
	}	

	public UserVo get( UserVo vo ) {
		return sqlSession.selectOne("user.selectByNameAndPassword", vo);
	}
	
	public void insert( UserVo vo ) {
		sqlSession.insert( "user.insert", vo );
	}
}
