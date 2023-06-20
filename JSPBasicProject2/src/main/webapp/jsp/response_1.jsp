<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//response.sendRedirect("response_2.jsp"); // sendRedirect() : 새로운 파일을 만드는것
	RequestDispatcher rd = request.getRequestDispatcher("response_2.jsp");
	rd.forward(request, response); // forward() : 기존에 있던 걸 붙여넣는것
	System.out.println(request);
%>