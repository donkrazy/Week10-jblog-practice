<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<ul>
	<c:choose>
		<c:when test='${empty authUser}' >
			<li><a href="${pageContext.request.contextPath}/user/login?next=${requestScope['javax.servlet.forward.servlet_path']}">로그인</a><li>
			<li><a href="${pageContext.request.contextPath}/user/join">회원가입</a><li>
		</c:when>
		<c:otherwise>
			<li><a href="#">내 정보(${authUser.name })</a><li>
			<li><a href="${pageContext.request.contextPath}/user/logout?next=${requestScope['javax.servlet.forward.servlet_path']}">로그아웃</a><li>
			<c:if test="${authUser.name==blogVo.name }">
			<li><a href="${pageContext.request.contextPath}/blog/admin">내 블로그 관리</a></li>
			<li><a href="${pageContext.request.contextPath}/blog/write">새 포스팅</a></li>
			</c:if>
		</c:otherwise>
	</c:choose>
			<li id="jblog"><a href="/">Jblog</a></li>
</ul>
