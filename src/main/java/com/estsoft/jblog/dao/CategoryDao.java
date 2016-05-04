package com.estsoft.jblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {
	@Autowired
	private SqlSession sqlSession;
	
	public CategoryVo get(int id){
		return sqlSession.selectOne("category.selectById", id);
	}
	
	public List<CategoryVo> getByBlogId(int id){
		return sqlSession.selectList("category.selectByBlogId", id );
	}
	public int popCategory(int blog_id){
		return sqlSession.selectOne("category.popCategory", blog_id );
	}
	public CategoryVo getByPostId(int id){
		return sqlSession.selectOne("category.selectByPostId", id);
	}
}
