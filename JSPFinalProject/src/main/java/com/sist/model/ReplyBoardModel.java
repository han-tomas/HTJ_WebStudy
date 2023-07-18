package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.common.*;
import com.sist.controller.RequestMapping;
public class ReplyBoardModel {
	@RequestMapping("replyboard/list.do")
	public String replyboard_list(HttpServletRequest request, HttpServletResponse response)
	{	
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		// 목록 전송 => DAO
		ReplyBoardDAO dao = ReplyBoardDAO.newInstance();
		List<ReplyBoardVO> list = dao.replyBoardListData(curpage);
		int totalpage = dao.replyBoardTotalPage();

		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("list", list);
		
		request.setAttribute("main_jsp", "../replyboard/list.jsp");
		CommonModel.commonReqeustData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("replyboard/insert.do")
	public String replyboard_insert(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("main_jsp", "../replyboard/insert.jsp");
		CommonModel.commonReqeustData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("replyboard/insert_ok.do")
	public String replyboard_insert_ok(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}catch(Exception ex) {}
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		HttpSession session=request.getSession();
		String id= (String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		ReplyBoardVO vo = new ReplyBoardVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		// DAO 연결
		ReplyBoardDAO dao = ReplyBoardDAO.newInstance();
		dao.replyBoardInsert(vo);
		return "redirect: ../replyboard/list.do";
	}
	@RequestMapping("replyboard/detail.do")
	public String replyboard_detail(HttpServletRequest request, HttpServletResponse response)
	{
		String no = request.getParameter("no");
		// 연동
		ReplyBoardDAO dao = ReplyBoardDAO.newInstance();
		ReplyBoardVO vo = dao.replyBoardDetailData(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../replyboard/detail.jsp");
		CommonModel.commonReqeustData(request);
		return "../main/main.jsp";
		
	}
	@RequestMapping("replyboard/delete.do")
	public String replyboard_delete(HttpServletRequest request, HttpServletResponse response)
	{
		String no=request.getParameter("no");
		ReplyBoardDAO dao = ReplyBoardDAO.newInstance();
		dao.replyBoardDelete(Integer.parseInt(no));
		return "redirect: ../replyboard/list.do";
	}
	@RequestMapping("replyboard/update.do")
	public String replyboard_update(HttpServletRequest request, HttpServletResponse response) {
		
		String no=request.getParameter("no");
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		ReplyBoardVO vo=dao.replyBoardUpdateData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../replyboard/update.jsp");
		return "../main/main.jsp";
	}
	
	@RequestMapping("replyboard/update_ok.do")
	public String replyboard_update_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {}
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String no=request.getParameter("no");
		ReplyBoardVO vo=new ReplyBoardVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setNo(Integer.parseInt(no));
		
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		dao.replyBoardUpdate(vo);
		
		return "redirect:../replyboard/detail.do?no="+no;
	}

	
}
