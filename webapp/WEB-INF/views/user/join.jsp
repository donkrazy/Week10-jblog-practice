<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<script>
$(function(){
	$("#check-id").click(function(){
		var name = $("#name").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/user/check-id/", 
			data:  {"name": name },
			success: function(response){
				if(response == "duplicated"){
					alert("중복된 아이디입니다");
					return false;
				}
				alert("사용가능");
				$("#check-id").hide();
			},
			error: function( xhr/*XMLHttpRequest*/, status, error ) {
				console.error( status + ":" + error );
			}				
		});
	})
	//client-validation	
	$(".join-form").submit( function() {
		if( $("#name").val() == "" ) {
			alert( "이름은 필수 요소입니다." );
			$("#name").focus();
			return false;	
		}
		if( $("#password").val() == "" ) {
			alert( "패스워드는 필수 요소입니다." );
			$("#password").focus();
			return false;	
		}
		if( $("#check-id").is( ":visible" ) == true ) {
			alert( "이메일 중복 체크를 해야 합니다." );
			return false;	
		}
		return true;	
	});
})
</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/menu.jsp" />
		<form class="join-form" id="join-form" method="post">
			<label class="block-label" for="name">아이디(영어, 숫자만 가능합니다)</label>
			<input id="name" name="name" type="text" value="">
			<spring:hasBindErrors name="userVo">
			   <c:if test="${errors.hasFieldErrors('name') }">
			        <br>
			        <strong style="color:red">${errors.getFieldError( 'name' ).defaultMessage }</strong>
			   </c:if>
			</spring:hasBindErrors>
			<input type="button" id="check-id" value="id중복체크" ></input>
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />
			<spring:hasBindErrors name="userVo">
			   <c:if test="${errors.hasFieldErrors('password') }">
			        <br>
			        <strong style="color:red">${errors.getFieldError( 'password' ).defaultMessage }</strong>
			   </c:if>
			</spring:hasBindErrors>
			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
