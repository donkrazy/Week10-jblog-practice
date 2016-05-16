package com.estsoft.jblog.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.estsoft.jblog.annotation.AuthUser;
import com.estsoft.jblog.dao.BlogDao;
import com.estsoft.jblog.dao.CategoryDao;
import com.estsoft.jblog.dao.PostDao;
import com.estsoft.jblog.vo.BlogVo;
import com.estsoft.jblog.vo.CategoryVo;
import com.estsoft.jblog.vo.PostVo;
import com.estsoft.jblog.vo.UserVo;
import com.estsoft.utils.FileUpload;

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
		System.out.println(blogVo);
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		CategoryVo firstCategory = categoryList.get(0);
		List<PostVo> postList = postDao.getByCategoryId(firstCategory.getId()); 
		PostVo postVo = postDao.popPostId(firstCategory.getId());
		//post.popCategory vs last element of list? ->
		model.addAttribute("name", name);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryVo", firstCategory);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
	}
	
	public void getBlogByCategoryId(String name, Model model, int cat){
		BlogVo blogVo = blogDao.get(name);
		if (blogVo==null){
			return;
		}
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		List<PostVo> postList = postDao.getByCategoryId(cat);
		CategoryVo categoryVo = categoryDao.get(cat);
		PostVo postVo;
		if(postList.size()==0){
			postVo = postDao.nullPost();
		}
		else{
			postVo = postList.get(postList.size()-1);
		}
		model.addAttribute("name", name);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		model.addAttribute("categoryVo", categoryVo);
	}
	
	public void getBlogByPostId(String name, Model model, int post_id){
		BlogVo blogVo = blogDao.get(name);
		if (blogVo==null){
			return;
		}
		List<CategoryVo> categoryList = categoryDao.getByBlogId(blogVo.getId());
		int category_id = postDao.getByPostId(post_id).getCategory_id();
		CategoryVo categoryVo = categoryDao.get(category_id);
		List<PostVo> postList = postDao.getByCategoryId(category_id);
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
		model.addAttribute("blogVo", blogVo);
	}
	
	public void addCategory(UserVo userVo, CategoryVo categoryVo){
		BlogVo blogVo = blogDao.get(userVo.getName());
		categoryVo.setBlog_id(blogVo.getId());
		categoryDao.insert(categoryVo);
	}
	public int addPost(UserVo userVo, PostVo vo){
		categoryDao.incrementCount(vo.getCategory_id());
		return postDao.insertPost(vo);
	}
	
	public void admin(UserVo userVo, Model model){
		BlogVo blogVo = blogDao.get(userVo.getName());
		model.addAttribute("blogVo", blogVo);
	}

	
	public List<UserVo> getAll(){
		return blogDao.getAll();
	}
	
	public boolean deletePost(int post_id, String blog_name, UserVo userVo, int category_id){
		if(userVo.getName().equals(blog_name)){
			postDao.deletePost(post_id);
			categoryDao.decrementCount(category_id);
			return true;
		}
		return false;
	}

	public boolean deleteCategory(UserVo authUser, String blog_name, int category_id) {
		if(authUser.getName().equals(blog_name)){
			categoryDao.delete(category_id);
			return true;
		}
		return false;
	}
	
	public void configBlog(UserVo authUser, BlogVo blogVo){
		blogVo.setName(authUser.getName());
		blogDao.update(blogVo);
	}

	public Map<String, Object> uploadLogo(MultipartHttpServletRequest request, @AuthUser UserVo userVo) {
		Iterator<String> itr = request.getFileNames(); /* 폼에 파일 선택이 여러개 있으면 여러개 나옴 */
		Map<String, Object> map = new HashMap<String, Object>();
		if(itr.hasNext()){ /* 지금은 하나라 if, 여러개면 while */
			//fileUpload
			MultipartFile mpf = request.getFile(itr.next());
			if(mpf.isEmpty() == false){ /* 파일 유효성 확인 */
				String fileOriginalName = mpf.getOriginalFilename(); //파일 이름
				String extName = fileOriginalName.substring( fileOriginalName.lastIndexOf(".") + 1, fileOriginalName.length() ); //파일 확장자
				String saveFileName = FileUpload.genSaveFileName( extName );
				File file = null;
				try {
					file = FileUpload.multipartToFile(mpf);
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
				//PUT the file to AWS S3 
				FileUpload.putAWS(saveFileName, file);
			    
			    //upload database
			    blogDao.updateLogo(userVo.getName(), saveFileName); //업로드한 파일명 DB에 저장
			    String imgurl = "/product-images/"+saveFileName;
				map.put("result", "success"); //response로 '결과 : 성공'을 보내줌
				map.put("data", imgurl); //response로 '데이터 : 파일URL'을 보내줌
			}
			return map;
		}else{	
			return null;
		}
	}
}
