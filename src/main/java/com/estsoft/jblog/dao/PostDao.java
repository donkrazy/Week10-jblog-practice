package com.estsoft.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.PostVo;

@Repository
public class PostDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<PostVo> getByCategoryId( int category_id ) {
		return sqlSession.selectList("post.selectByCategoryId", category_id );
	}
	
	public PostVo getByPostId(int post_id){
		return sqlSession.selectOne("post.selectByPostId", post_id);
	}
	
	//TODO: 카테고리에 글이 없을 때
	public int popPostId(int category_id){
		return sqlSession.selectOne("post.popPostId", category_id );
	}

}
