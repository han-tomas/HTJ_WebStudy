package com.sist.food;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.sist.dao.*;
/*
 *   @GetMapping => GET
 *   @PostMapping => POST
 *   @RequestMapping => GET/POST
 *   ----------------------------- 안맞으면 400오류 발생
 */

@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// 사용자가 요청한 값을 받는다
		String addr = request.getParameter("addr");
		if(addr==null)
			addr="마포"; // 검색어가 없을때의 디폴트값 설정
		
		String strPage=request.getParameter("page");
		if(strPage==null)
			strPage="1";
		
		int curpage=Integer.parseInt(strPage);
		
		FoodDAO dao=FoodDAO.newInstance();
		List<FoodVO> list = dao.foodFindData(addr,curpage);
		int totalpage=(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);
		final int BLOCK=5;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		// < [1] [2] [3] [4] [5] >
		// < [6] [7] [8] [9] [10] >
		// 화면
		/*
		  	<ul class="pagination">
	        <li><a href="#">1</a></li>
	        <li class="active"><a href="#">2</a></li>
	        <li><a href="#">3</a></li>
	        <li><a href="#">4</a></li>
	        <li><a href="#">5</a></li>
      		</ul>
		 */
		PrintWriter out = response.getWriter();
		// 페이징 기법
		
	}

}
