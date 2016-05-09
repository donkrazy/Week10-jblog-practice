package com.estsoft.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.estsoft.jblog.dao.BlogDao;
import com.estsoft.jblog.dao.CategoryDao;
import com.estsoft.jblog.dao.PostDao;
import com.estsoft.jblog.vo.BlogVo;
import com.estsoft.jblog.vo.CategoryVo;
import com.estsoft.jblog.vo.PostVo;
import com.estsoft.jblog.vo.UserVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;
	
	public void getBlogByName(String name, Model model){
		BlogVo blogVo = blogDao.get(name);
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		CategoryVo lastCategory = categoryList.get(categoryList.size()-1);
		List<PostVo> postList = postDao.getByCategoryId(lastCategory.getId()); 
		PostVo postVo = postDao.popPostId(lastCategory.getId());
		//post.popCategory vs last element of list? ->
		model.addAttribute("name", name);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryVo", lastCategory);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
	}
	
	public boolean getBlogByCategoryId(String name, Model model, int cat){
		BlogVo blogVo = blogDao.get(name);
		if (blogVo==null){
			return false;
		}
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		List<PostVo> postList = postDao.getByCategoryId(cat);
		CategoryVo categoryVo = categoryDao.get(cat);
		if(postList.size()==0){
			return false;
		}
		PostVo postVo = postList.get(postList.size()-1);
		model.addAttribute("name", name);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("categoryVo", categoryVo);
		return true;
	}
	
	public void getBlogByPostId(String name, Model model, int post_id){
		BlogVo blogVo = blogDao.get(name);
		if (blogVo==null){
			return;
		}
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		CategoryVo categoryVo = categoryList.get(categoryList.size()-1);
		List<PostVo> postList = postDao.getByCategoryId(categoryVo.getId());
		PostVo postVo = postDao.getByPostId(post_id);
		model.addAttribute("name", name);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("categoryVo", categoryVo);
	}
	
	
	public void listCategory(UserVo userVo, Model model){
		BlogVo blogVo = blogDao.get(userVo.getName());
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		model.addAttribute("categoryList", categoryList);
	}
	
	public void addCategory(UserVo userVo, CategoryVo categoryVo){
		BlogVo blogVo = blogDao.get(userVo.getName());
		categoryVo.setBlog_id(blogVo.getId());
		categoryDao.insert(categoryVo);
	}
}
