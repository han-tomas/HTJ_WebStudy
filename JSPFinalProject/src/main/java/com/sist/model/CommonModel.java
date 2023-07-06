package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
// => Spring (공통 모듈 =>AOP)
// Spring => DI / AOP (사용법 : XML, Annotation)
public class CommonModel {
	public static void commonReqeustData(HttpServletRequest request)
	{
		// footer
		FoodDAO dao = FoodDAO.newInstance();
		
		// => 공지사항 => 최신뉴스
		// => 방문 맛집 
		List<FoodVO> fList = dao.foodTop7();
		request.setAttribute("fList", fList);
		
	}
}
