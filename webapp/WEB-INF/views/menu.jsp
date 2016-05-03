<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<h1 class="logo" onclick="window.location='/';">JBlog</h1>
<ul class="menu">
	<c:choose>
		<c:when test='${empty authUser}' >
			<li><a href="${pageContext.request.contextPath}/user/login?next=${requestScope['javax.servlet.forward.servlet_path']}">로그인</a><li>
			<li><a href="${pageContext.request.contextPath}/user/joinform">회원가입</a><li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.request.contextPath}/user/modifyform">${authUser.name } 회원정보수정</a><li>
			<li><a href="${pageContext.request.contextPath}/user/logout?next=${requestScope['javax.servlet.forward.servlet_path']}">로그아웃</a><li>
		</c:otherwise>
	</c:choose>
			<li><a href="/blog/">내블로그</a></li>
</ul>