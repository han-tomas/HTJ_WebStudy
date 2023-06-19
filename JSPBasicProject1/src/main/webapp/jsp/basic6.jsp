<%--
	import => 라이브러리
	import="java.util.*,java.io.*";
	
 --%>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.*,com.sist.*" buffer="16kb" %>
<%
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String today=sdf.format(date);
	MainClass m = new MainClass();
	String msg=m.display();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	오늘날짜는 <%=today %>
	<h1><%=msg %></h1>
	<h1>총 버퍼 :<%=out.getBufferSize() %></h1>
	<h1>남은 버퍼 :<%=out.getRemaining() %></h1>
	<h1>사용중인 버퍼 :<%=out.getBufferSize() - out.getRemaining() %></h1>
</body>
</html>