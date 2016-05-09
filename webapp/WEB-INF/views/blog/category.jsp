<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	// ajax 방명록 메세지 등록
	$( "#admin-cat-add" ).submit( function( event ) {
		event.preventDefault(); 
		var name = $( "#name" ).val();
		var description = $( "#description" ).val();
		this.reset();
		$.ajax({
			url:"${pageContext.request.contextPath }/blog/category/", 
			type: "post",
			//SANDBOX: 잭슨라이브러리 안씀
			//dataType: "json",
			//key-value pair로 보내면 된다고 한다. dataType: json명시 안해도 json으로 가는듯(.ajax가 json형식이니까)
			data:  {"name": name, "description" : description },
			success: function( response  ){
				if(response == "success"){
					$("#admin-cat").append( renderHTML(name, description) );	
				}
			},
			error: function( xhr/*XMLHttpRequest*/, status, error ) {
				console.error( status + ":" + error );
			}				
		});
	});
	
	var renderHTML = function(name, description){
		var catLength = Number( '${fn:length(categoryList)}' ) +1 ;
		var html = "<tr>"+
					"<td>" + catLength + "</td>" +
					"<td>" + name + "</td>" +
					"<td>" + 0 + "</td>" +
					"<td>" + description + "</td>" +
					"<td><img src='" + "${pageContext.request.contextPath}/assets/images/delete.jpg" + "'/></td>" +
					"</tr>"	;
		return html;
	}
});
</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<c:import url="/WEB-INF/views/blog/top.jsp" />
		</div>
		<div id="header">
			<h1 onclick=" window.location='/blog/';">Spring 이야기</h1>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/blog/admin">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/blog/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat" id="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		
		      		
		      		<c:set var="categories-count" value="${fn:length(categoryList) }" />
		      		<c:forEach items="${categoryList }" var="vo" varStatus="status">
						<tr>
							<td>${status.count }</td>
							<td>${vo.name }</td>
							<td>${vo.count }</td>
							<td>${vo.description }</td>
							<td><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></td>
						</tr>
					</c:forEach>
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<form id="admin-cat-add">
			      	<table>
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" id="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" id="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table>
		      	</form> 
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>