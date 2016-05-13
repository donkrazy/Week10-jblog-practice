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
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/menu.jsp" />
		<form method="POST" class="login-form">
      		<label>아이디</label> <input type="text" name="name" >
      		<label>패스워드</label> <input type="password" name="password" >
      		<input type="submit" value="로그인">
		</form>
	</div>
</body>
</html>
