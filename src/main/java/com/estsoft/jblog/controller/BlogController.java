package com.estsoft.jblog.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.estsoft.jblog.annotation.Auth;
import com.estsoft.jblog.annotation.AuthUser;
import com.estsoft.jblog.service.BlogService;
import com.estsoft.jblog.vo.BlogVo;
import com.estsoft.jblog.vo.CategoryVo;
import com.estsoft.jblog.vo.PostVo;
import com.estsoft.jblog.vo.UserVo;
import com.estsoft.utils.FileUpload;

@Controller
@RequestMapping( "/blog" )
public class BlogController {
	private static final String SAVE_PATH = "/springfile";
	
	@Autowired
	private BlogService blogService;
	
	@Auth
	@RequestMapping( "/" )
	public String root(@AuthUser UserVo authUser) {
		return "redirect:/blog/"+authUser.getName();
	}
		
	@RequestMapping( "/{name}" )
	public String blogMain(@PathVariable("name") String name, Model model, @RequestParam(value = "cat", required=true, defaultValue="0") int cat) {
		if(cat==0){
			//TODO: 카테고리 없이 접근했을때 그냥 블로그의 기본 카테고리로 redirect시키면 될듯
			blogService.getBlogByName(name, model);
			return "blog/main";
		}
		//TODO: 포스트가 존재하지 않는 카테고리일 때 처리
		//TODO: 또라이 유저들이 cat에 이상한 입력할때. 근데 string넣으면 400에러뜬다. 400에러페이지 만들기
		blogService.getBlogByCategoryId(name, model, cat);
		return "blog/main";
	}

	
	//TODO: name과 post_id는 종속. 글쓴이에 해당하지 않는 요청 처리
	@RequestMapping( "/{name}/{post_id}" )
	public String getBlogByPostId(@PathVariable("name") String name, Model model, @PathVariable("post_id") int post_id) {
		blogService.getBlogByPostId(name, model, post_id);
		return "blog/main";
	}
	
	@Auth
	@RequestMapping( value = "/admin", method=RequestMethod.GET )
	public String admin(Model model, @AuthUser UserVo authUser) {
		blogService.admin(authUser, model);
		return "blog/admin";
	}
	

	@Auth
	@RequestMapping( value="/category", method=RequestMethod.GET )
	public String category(Model model , @AuthUser UserVo authUser) {
		blogService.listCategory(authUser, model);
		return "blog/category";
	}
	
	@Auth
	@ResponseBody
	//리턴값 json이 아닌 그냥 string
	@RequestMapping( value="/category", method=RequestMethod.POST )
	public String addCategory(@AuthUser UserVo authUser, @ModelAttribute @Valid CategoryVo categoryVo, BindingResult result) {
		if ( result.hasErrors() ) {
			return "";
		}
		blogService.addCategory(authUser, categoryVo);
		String addedCategoryId = String.valueOf(categoryVo.getId());
		return addedCategoryId;
	}
	
	@Auth
	@RequestMapping( value="/write", method=RequestMethod.GET )
	public String writeform(Model model , @AuthUser UserVo authUser) {
		blogService.listCategory(authUser, model);
		return "blog/write";
	}
	
	@Auth
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String write(@AuthUser UserVo authUser, @ModelAttribute @Valid PostVo postVo, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
			model.addAllAttributes( result.getModel() );
			blogService.listCategory(authUser, model);
			return "blog/write";
		}
		int postId = blogService.addPost(authUser, postVo);
		return "redirect:/blog/"+authUser.getName()+"/"+postId;
	}
	
	@Auth
	@RequestMapping( value="/delete", method=RequestMethod.POST)
	public String deletePost(@AuthUser UserVo authUser, @RequestParam int post_id, @RequestParam String blog_name, @RequestParam int category_id){
		boolean isDeleted = blogService.deletePost( post_id, blog_name, authUser, category_id );
		if(isDeleted){
			String category_url = blog_name+"?cat="+category_id;
			return "redirect:/blog/"+ category_url;
		}
		return "blog/deletefail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete-cat", method = RequestMethod.POST)
	public String deleteCategory(@AuthUser UserVo authUser, @RequestParam String blog_name, @RequestParam int category_id) {
		boolean isDeleted = blogService.deleteCategory(authUser, blog_name, category_id);
		if(isDeleted){
			return "success";
		}
		return null;
	}
	
	
	@Auth
	@RequestMapping( value = "/admin", method=RequestMethod.POST )
	public String adminConfig(@AuthUser UserVo authUser, @ModelAttribute BlogVo blogVo,  Model model) {
		//TODO: 블로그 주인이 아닐 경우 redirect??
	        blogService.configBlog(authUser, blogVo);
		return "redirect:/blog/admin/";
	}
	
	
	//TODO: ajax 파일 업로드
	@Auth
	@RequestMapping(value="/logo", method=RequestMethod.POST)
	@ResponseBody
	public Object ajaxList(MultipartHttpServletRequest request, @AuthUser UserVo authUser){	
		Iterator<String> itr = request.getFileNames(); /* 폼에 파일 선택이 여러개 있으면 여러개 나옴 */
		Map<String, Object> map = new HashMap<String, Object>();
		if(itr.hasNext()){ /* 지금은 하나라 if, 여러개면 while */
			//fileUpload
			MultipartFile mpf = request.getFile(itr.next());
			if(mpf.isEmpty() == false){ /* 파일 유효성 확인 */
				String fileOriginalName = mpf.getOriginalFilename(); //파일 이름
				String extName = fileOriginalName.substring( fileOriginalName.lastIndexOf(".") + 1, fileOriginalName.length() ); //파일 확장자
				String saveFileName = FileUpload.genSaveFileName( extName );
				FileUpload.writeFile(mpf, SAVE_PATH, saveFileName);
				map.put("result", "success"); //response로 '결과 : 성공'을 보내줌
				map.put("data", saveFileName); //response로 '데이터 : 파일URL'을 보내줌
				//upload database
				blogService.updateLogo(authUser, saveFileName); //업로드한 파일명 DB에 저장
			}
			return map;
		}else{	
			return null;
		}
	}

}