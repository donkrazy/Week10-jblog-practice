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
		//post가 없는 카테고리일 때
		if (post_id==0){
			return nullPost();
		}
		return sqlSession.selectOne("post.selectByPostId", post_id);
	}
	
	public PostVo popPostId(int category_id){
		//빈 카테고리일 때
		if(sqlSession.selectOne("post.popPostId", category_id )==null){
//			return sqlSession.selectOne("post.selectNullPost");
			return nullPost();
		}
		return sqlSession.selectOne("post.popPostId", category_id );
	}
	
	public int insertPost(PostVo postVo){
		sqlSession.insert("post.insertPost", postVo);
		System.out.println(postVo);
		return postVo.getId();
	}
	
	public void deletePost(int post_id){
		sqlSession.delete("post.delete", post_id);
	}
	
	public PostVo nullPost(){
		PostVo vo = new PostVo();
		vo.setTitle("빈 카테고리입니다");
		vo.setContent("글좀 써라");
		return vo;
	}
}
