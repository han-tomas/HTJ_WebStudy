<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	application : ServletContext
	=> 서버 관리 ( 서버에 대한 정보, 로그의 정보, 자원관리 )
		= 서버 버전 : servlet의 버전
			1) 서버이름 : getServerInfo() : 톰캣
			2) getMajorVersion()
			3) getMinorVersion()
				17.01, 3.5 ...
				-- --
			 major minor
			4) 로그 => log 
			5) 자원관리 (파일) => getRealPath()
			
			 
 --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
서버이름:<%= application.getServerInfo() %><br>
버젼:<%= application.getMajorVersion() %><br>
부버젼:<%= application.getMinorVersion() %><br>
<%-- MIME:<%= application.getMimeType("applicaton_1.jsp") %><br> --%>
</body>
</html>