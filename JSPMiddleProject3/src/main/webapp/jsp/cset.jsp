<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	String name="홍길동";
	// request에 저장
	request.setAttribute("name", "심청이");
%>
<c:set var="name" value="박문수"/><%-- EL에서만 출력이 가능하게 만든 변수설정 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>자바에서 출력</h1>
	이름1:&nbsp;<%=name %><br>
	이름2:&nbsp;<%=request.getAttribute("name") %>
	
	<h1>JSTL에서 출력</h1>
	이름3:&nbsp;${name } <br>
	<%-- Jquery와 충돌 방지
		 출력 => %
		 VueJS =>{{}}
		 React => {}
	 --%>
	이름4:&nbsp;<c:out value="${name }"/> <br>
	이름5:&nbsp;<c:out value="<%=name %>"/> <br>
	이름6:&nbsp;<c:out value="이순신"/> <br>
	<%-- 자바스크립트에서 JSTL사용이 가능 --%>
</body>
</html>