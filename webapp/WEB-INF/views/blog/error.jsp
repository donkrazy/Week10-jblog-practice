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
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
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
			<div id="content">
				포스트가 존재하지 않습니다
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img
					src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">
			</div>
		</div>

		<div id="navigation">
			
		</div>

		<div id="footer">
			<p>
				
			</p>
		</div>
	</div>
</body>
</html>