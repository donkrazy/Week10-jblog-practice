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
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/menu.jsp" />
		<form class="join-form" id="join-form" method="post">
			<label class="block-label" for="name">아이디</label>
			<input id="name" name="name" type="text" value="${authUser.name }">
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />
			<input type="submit" value="회원정보수정">
		</form>
	</div>
</body>
</html>
