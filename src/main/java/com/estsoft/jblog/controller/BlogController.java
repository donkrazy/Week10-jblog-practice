package com.estsoft.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	public String root() {
		return "/index";
	}
	
	@RequestMapping( "/{name}" )
	public String blogMain(@PathVariable("name") String name, Model model, @RequestParam(value = "cat", required=true, defaultValue="0") int cat) {
		if(cat==0){
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
	@RequestMapping( value = "/admin", method=RequestMethod.POST )
	public String adminConfig(@AuthUser UserVo authUser, @ModelAttribute BlogVo blogVo,  @RequestParam( "file" ) MultipartFile file, Model model) {
		//TODO: 블로그 주인이 아닐 경우 redirect??
		if( file.isEmpty() == false ) {
	        String fileOriginalName = file.getOriginalFilename();
	        String extName = fileOriginalName.substring( fileOriginalName.lastIndexOf(".") + 1, fileOriginalName.length() );
	        String saveFileName = FileUpload.genSaveFileName( extName );
	        FileUpload.writeFile( file, SAVE_PATH, saveFileName );
	        String url = "/product-images/" + saveFileName;
	        model.addAttribute( "logo", url );
		}
		blogService.configBlog(authUser, blogVo);
		return "redirect:/blog/admin";
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
	public String addCategory(@AuthUser UserVo authUser, @ModelAttribute CategoryVo categoryVo) {
		blogService.addCategory(authUser, categoryVo);
		return "success";
	}
	
	@Auth
	@RequestMapping( value="/write", method=RequestMethod.GET )
	public String writeform(Model model , @AuthUser UserVo authUser) {
		blogService.listCategory(authUser, model);
		return "blog/write";
	}
	@Auth
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String write(@AuthUser UserVo authUser, @ModelAttribute PostVo postVo) {
		int postId = blogService.addPost(authUser, postVo);
		return "redirect:/blog/"+authUser.getName()+"/"+postId;
	}

}