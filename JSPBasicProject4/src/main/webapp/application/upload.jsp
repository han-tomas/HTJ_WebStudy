<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.oreilly.servlet.*"%>
<%@ page import ="com.oreilly.servlet.multipart.*"%>
<%
	request.setCharacterEncoding("UTF-8"); // post방식일 경우 인코딩이 필요하다 / get은 필요 X
	//String path="C:\\webDev\\webStudy\\JSPBasicProject4\\src\\main\\webapp\\image";
	//C:\webDev\webStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\JSPBasicProject4\image 
	// => Realpath
	String path= application.getRealPath("/image");
	int max=1024*1024*100;
	String enctype="UTF-8";
	MultipartRequest mr = new MultipartRequest(request,path,max,enctype,new DefaultFileRenamePolicy());
	String name = mr.getOriginalFileName("upload");
	
	// 이동
	response.sendRedirect("list.jsp?fn="+name);
	
%>