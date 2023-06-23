<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*" %>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no = request.getParameter("no"); // 삭제를 위한 변수
	String bno = request.getParameter("bno"); // 댓글을 삭제한 게시물로 다시 돌아가기 위한 변수
	
	dao.replyDelete(Integer.parseInt(no));
	
	// 이동
	response.sendRedirect("detail.jsp?no="+bno);
%>
