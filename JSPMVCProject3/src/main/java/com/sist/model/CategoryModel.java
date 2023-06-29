package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
// 			request   requset
// Controller => model => JSP
/*
 		class A
 		{
 			int aa;
 		}
 		
 		public void set(A a)
 		{
 			a.aa=100;
 		}
 		
 		A b= new A();
 		set(b);
 */
public class CategoryModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CategoryModel: "+request);
		FoodDAO dao = FoodDAO.newInstance();
		List<CategoryVO> list = dao.foodCategoryData();
		// request : DispatcherServlet
		request.setAttribute("list", list);
		
		return "food/category.jsp";
	}

}
