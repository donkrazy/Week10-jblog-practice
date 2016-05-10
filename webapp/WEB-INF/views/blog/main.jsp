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
				<div class="blog-content">
					<p>카테고리-${categoryVo.name}</p>
					<h4>${postVo.title }</h4>
					<p>${postVo.reg_date }</p>
					<p>${postVo.content }</p>
				</div>
				<br><br>----------------------------------------------------------------관련 포스팅-------------------------------------------------------------------
				<ul class="blog-list">
					<c:forEach items="${postList }" var="postVo" varStatus="status">
						<li><a href="/blog/${blogVo.name}/${postVo.id}">${postVo.title}</a><span>${postVo.reg_date }</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/assets/images/spring-logo.jpg">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList }" var="categoryVo" varStatus="status">
					<li><a href="/blog/${blogVo.name}?cat=${categoryVo.id}">${categoryVo.name}</a></li>
				</c:forEach>
			</ul>
		</div>

		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>