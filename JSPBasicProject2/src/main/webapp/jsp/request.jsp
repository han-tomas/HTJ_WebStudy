<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	JSP
	---
	 1. 동작 순서
	 	------------------------
	 	1) client 요청 (주소창을 이용해서 서버에 연결)
	 		http://localhost:8080/JSPBasicProject2/jsp/request.jsp
	 		-----  --------- ---- ----------------
	 	   protocol serverIP port	ContextPath
	 		--------------------- ---------------------------------
	 			   서버관련					클라이언트 요청 관련(URI)
	 			   --------------------------------------- 
	 			   					URL
	 	2) DNS를 거쳐서 => localhost(도메인) => ip변경
	 	3) ip/port를 이용해서 서버에 연결
	 	   new socket(ip,port) => TCP
	 	---------------------------------
	 	4) Web Server
	 		httpd
	 		-----
	 			= HTML,XML,CSS,JSON => Web Server 자체에서 처리후에 브라우저로 전송
	 			= JSP / Servlet은 처리하지 못한다.
	 			  --------------------------
	 			  			|
	 			  		Web Container(WAS) => Java로 변경
	 			  						   => 컴파일
	 			  						   => 실행
	 			  						   	  ---
	 			  						   	  실행결과를 메모리에 모아둔다		   					
	 	5) 메모리에 출력한 내용을 브라우저로 응답		  						   	  
	 	
	 	JSP(Java Server Page) : 서버에서 실행되는 자바파일
	 	-------------------------------------------
	 		_jspInit() => web.xml => 초기화
	 		_jspService() => 사용자 요청을 처리하고 결곽값을 HTML로 전송
	 		------------- 공백
	 		{
	 			소스 코딩 => JSP
	 		}
	 		_jspDestroy() => 새로고침, 화면 이동 ... 메모리에서 해제
	 		public void _jspInit() {
			}
			
			public void _jspDestroy() {
			}
	 		
	 		public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
		    {
			    final javax.servlet.jsp.PageContext pageContext;
			    javax.servlet.http.HttpSession session = null;
			    final javax.servlet.ServletContext application;
			    final javax.servlet.ServletConfig config;
			    javax.servlet.jsp.JspWriter out = null;
			    final java.lang.Object page = this;
			    javax.servlet.jsp.JspWriter _jspx_out = null;
			    javax.servlet.jsp.PageContext _jspx_page_context = null;
			    
			    소스 코딩 영역
			    ==> JSP
			}
	 		
	 2. 지시자
	 	page 형식) <%@ page 속성="" 속성=""%>
	 	----
	 	 JSP파일에 대한 정보
	 	 속성 : 
	 	 		contentType="" : 브라우저에 어떤 파일인지 알려준다
	 	 						 ----- HTML/ XML/ JSON (외에는 일반 텍스트)
	 	 					   		   ----  ---  ----
	 	 						  	   	|	  |	  text/plain => RestFul
	 	 						  		|	 text/xml
	 	 						  	   text/html
	 	 		import : 라이브러리 읽기
	 	 			import="java.util.*,java.io.*"
	 	 		errorPage : error시에 이동하는 페이지 지정
	 	 		buffer = "8kb" =>16kb 32kb ... 				  	     
	 3. 스크립트 사용법
	 	자바가 코딩되는 영역
	 	<%! %> : 선언문(메소드, 멤버변수) => 사용빈도가 거의 없다.
	 	<%  %> : 자바 코딩(일반 자바) => 제어문, 메소드 호출, 지역변수 ..
	 	<%= %> : 화면 출력
	 			 out.println()
	 	JSP = Model1은 2003유행 => model2(MVC) => Domain(MSA)
	 		  ---------------		|				|
	 		  					   Spring4		Spring5/Spring6
	 	=> 표현식, 스크립트릿
	 		${}	   JSTL
	 	=> JSP안에서는 태그형으로 제작
	 	  					   
	 4. 내장객체
	 	=> 165page
	 	9가지 지원
	 	*****= request => HttpServletRequest
	 			request는 관리자 => 톰캣
	 			1) 서버정보/클라이언트 브라우저 정보
	 				getServerInfo()
	 				getPort()
	 				getMethod()
	 				getProtocol()
	 			 ***getRequestURL()
	 			 ***getRequestURI()
	 			 ***getContextPath()
	 			2) 사용자 요청 정보
	 				데이터 전송시 => 데이터가 request에 묶여서 들어온다
	 				= 단일 데이터
	 					String getParameter()
	 				= 다중 데이터
	 					String[] getParameterValues() => checkbox, select(multiline)
	 				= 한글 변환 (디코딩)
	 					setCharacterEncoding => UTF-8
	 				= 키를 읽는다
	 				 	getParameterNames()
	 				 	받는파일명?no=1&name=aaa
	 				 	-------
	 				 	a.jsp?no=1&name=aaa&hobby=b&hobby=c	
	 			3) 추가 정보
	 				setAttribute() : request 데이터 추가전송
	 				getAttribute() : 전송된 데이터 읽기
	 		   *****= response => HttpServletResponse
	 				= Header 정보
	 					다운로드 => 파일명, 크기
	 					setHeader()
	 				= 응답 정보
	 					= HTML전송 => text/html
	 					= Cookie전송 => addCookie
	 				= 화면 이동
	 					= sendRedirect()		
	 	*****= response => HttpServletResponse
	 	*****= session => HttpSession
	 	   **= out	=> JspWriter
	 	  ***= application => ServletContext
	 	*****= pageContext => PageContext
	 		 = page	=> Object
	 		 = exception => Exception
	 		 = config => ServletConfig
	 	-------------------------------------------
	 		페이지 입출력
	 			request, response, out
	 	-------------------------------------------
	 		외부 환경 정보
	 			config
	 	-------------------------------------------
	 		서블릿 관련
	 			application, pageContext, session
	 	-------------------------------------------
	 		예외처리 관련
	 			exception
	 	-------------------------------------------
	 			 
	 5. 액션태그
	 6. include
	 7. cookie
	 8. JSTL
	 9. EL
	 10. MVC
	 
	 String a = request.getParameter("a");
	 String b = request.getParameter("b");
	 String c = request.getParameter("c");
	 String d = request.getParameter("d");
	 String e = request.getParameter("e");
	 String f = request.getParameter("f");
	 String g = request.getParameter("g");
	 String h = request.getParameter("h");
	 String i = request.getParameter("i");
	 String j = request.getParameter("j");
	 
	 <jsp:setProperty name="vo" property="*"/>
 --%>   
 <%
 	pageContext.include("");
 %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>