<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/blog/admin">기본설정</a></li>
					<li><a href="${pageContext.request.contextPath}/blog/category">카테고리</a></li>
					<li class="selected">글작성</li>
				</ul>
				<form method="post">
					<table class="admin-cat-write">
						<tr>
							<td class="t">제목</td>
							<td><input type="text" size="60" name="title" />
							<spring:hasBindErrors name="postVo">
									<c:if test="${errors.hasFieldErrors('title') }">
										<br>
										<strong style="color: red">${errors.getFieldError( 'title' ).defaultMessage }</strong>
									</c:if>
								</spring:hasBindErrors>
							<select	name="category_id">
									<c:forEach items="${categoryList }" var="vo" varStatus="status">
										<option value="${vo.id }">${vo.name }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="t">내용</td>
							<td><textarea name="content"></textarea></td>
						</tr>
						<spring:hasBindErrors name="postVo">
								<c:if test="${errors.hasFieldErrors('content') }">
									<br>
									<strong style="color: red">${errors.getFieldError( 'content' ).defaultMessage }</strong>
								</c:if>
							</spring:hasBindErrors>
							
						<tr>
							<td>&nbsp;</td>
							<td class="s"><input type="submit" value="포스트하기"></td>
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