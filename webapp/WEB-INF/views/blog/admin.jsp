<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script>
	$(function() {
		$("input[type='file']").change(	function() {
			$file = $(this).val();
			if ($file == null || $.isEmptyObject($file)) return;
			var formData = new FormData(document.getElementById('logo'));
			$.ajax({
				url : "${pageContext.request.contextPath}/blog/logo",
				data : formData,
				dataType : "json",
				processData : false,
				contentType : false,
				type : "POST",
				success : function(response) {
					var imgurl = "${pageContext.request.contextPath}" + response.data;
					$("#logo-image").attr("src", imgurl);
				},
				error : function(xhr, status, error) {	console.error(status + " : " + error);
				}
			})
		})
	})
</script>
</head>
<body>
	<div id="container">
		<div id="top">
			<c:import url="/WEB-INF/views/blog/top.jsp" />
		</div>
		<div id="header">
			<h1 onclick=" window.location='/blog/${blogVo.name}';">${blogVo.title }</h1>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected">기본설정</li>
					<li><a href="${pageContext.request.contextPath}/blog/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath}/blog/write">글작성</a></li>
				</ul>
				<img id="logo-image" src="/product-images/${blogVo.logo}"> 로고이미지 변경
				<form id="logo">
					<input type="file" name="file">
				</form>
				<form method="post" enctype="multipart/form-data">
					<table class="admin-config">
						<tr>
							<td class="t">블로그 제목</td>
							<td><input type="text" size="40" name="title"
								value="${blogVo.title }"></td>
						</tr>
						<tr>
							<td class="t">&nbsp;</td>
							<td class="s"><input type="submit" value="기본설정 변경"></td>
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