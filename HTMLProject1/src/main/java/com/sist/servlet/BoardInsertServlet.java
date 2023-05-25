package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
@WebServlet("/BoardInsertServlet")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 입력폼 전송 => HTML (HTML을 브라우저로 보낸다)
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=html/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글쓰기</h1>");
		// 입력된 데이터를 한 번에 action에 등록된 클래스로 전송 (input에 입력된 데이터만 전송)
		// 메소드 => method=post이면 doPost() 호출
		out.println("<form method=post action=BoardInsertServlet>");
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<th width=15%>이름</th>");
		out.println("<td width=85%><input type=text name=name size=15 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>제목</th>");
		out.println("<td width=85%><input type=text name=subject size=50 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>내용</th>");
		out.println("<td width=85%><textarea rows=10 cols=50 name=content required></textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th width=15%>비밀번호</th>");
		out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=글쓰기>");
		out.println("<input type=button value=취소 onclick=\"jacascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}
	/* 
	 * 보낼때 인코딩 받을때 디코딩
	 * http://localhost:8080/HTMLProject1/BoardInsertServlet?
	 * name=%ED%95%9C%ED%83%9C%EC%A4%80&
	 * subject=%EC%B2%98%EC%9D%8C+%EB%A7%8C%EB%93%9C%EB%8A%94+%EA%B2%8C%EC%8B%9C%ED%8C%90&
	 * content=%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94&
	 * pwd=1234
	 * ==> 보낼때 바이트 코드로 보낸다.
	 * %ED%95%9C%ED%83%9C%EC%A4%80& => 인코딩
	 * 한태준 => 디코딩
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			request.setCharacterEncoding("UTF-8"); // 한글변환 ***setCharacterEncoding
			// 디코딩 => byte[](바이트배열)을 한글로 변환
			// 자바 => 2byte => 브라우저는 1byte로 받는다 
			
			// %ED%95%9C%ED%83%9C%EC%A4%80& => 인코딩
			// 한태준 => 디코딩
			
			// 값을 받는다 *** getParameter
			String name = request.getParameter("name");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String pwd = request.getParameter("pwd");
			
			/*System.out.println("이름 : "+name);
			System.out.println("제목 : "+subject);
			System.out.println("내용 : "+content);
			System.out.println("비밀번호 : "+pwd);*/
			BoardVO vo = new BoardVO();
			vo.setName(name);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setPwd(pwd);
			
			// 오라클로 INSERT 요청
			BoardDAO dao = BoardDAO.newInstance();
			dao.boardInsert(vo);
			
			// 화면이동 ***sendRedirect
			response.sendRedirect("BoardListServlet");
		}catch(Exception ex) {}
		
	}

}
