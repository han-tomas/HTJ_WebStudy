package com.sist.manager;
import java.util.*;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.*;
import com.sist.vo.*;
public class DataCollection {
	public void foodCategoryData()
	{
		FoodDAO dao = FoodDAO.newInstance();
		try
		{
			// 사이트 연결
			Document doc = Jsoup.connect("https://www.mangoplate.com/").get();
			Elements title = doc.select("div.info_inner_wrap span.title");
			Elements subject = doc.select("div.info_inner_wrap p.desc");
			Elements poster = doc.select("ul.list-toplist-slider img.center-croping");
			Elements link = doc.select("ul.list-toplist-slider a");
			/*System.out.println(title.toString());
			System.out.println("==================================================");
			System.out.println(subject.toString());
			System.out.println("==================================================");
			System.out.println(poster.toString());
			System.out.println("==================================================");
			System.out.println(link.toString());*/
			for(int i=0;i<title.size();i++)
			{
				System.out.println(title.get(i).text());
				System.out.println(subject.get(i).text());
				System.out.println(poster.get(i).attr("data-lazy"));
				System.out.println(link.get(i).attr("href"));
				CategoryVO vo = new CategoryVO();
				vo.setTitle(title.get(i).text());
				vo.setSubject(subject.get(i).text());
				vo.setLink(link.get(i).attr("href"));
				String p=poster.get(i).attr("data-lazy");
				/*
				 * https://mp-seoul-image-production-s3.mangoplate.com/
				 * keyword_search/meta/pictures/
				 * he53hhdkhy_-fvxz.jpg?fit=around|600:400&crop=600:400;
				 * *,*&output-format=jpg&output-quality=80
				 * 
				 * ==> &에서 문제가 생긴다.
				 */
				p= p.replace("&", "#");
				vo.setPoster(p);
				dao.foodCategoryInsert(vo);
				
			}
			System.out.println("저장완료!!");
		}catch(Exception ex) {}
		
	}
	public void foodDetailData()
	{
		FoodDAO dao = FoodDAO.newInstance();
		try
		{
			List<CategoryVO> list = dao.foodCategoryData();
			for(CategoryVO vo:list)
			{
				Document doc = Jsoup.connect(vo.getLink()).get();
				Elements link=doc.select("div.info span.title a");
				// 카테고리별
				for(int i=0;i<link.size();i++)
				{
					System.out.println(link.get(i).attr("href"));
					Document doc2 = Jsoup.connect("https://www.mangoplate.com"+link.get(i).attr("href")).get();
				}
			}
		}catch(Exception ex) {}
		
	}
	public static void main(String[] args) {
		DataCollection dc = new DataCollection();
		dc.foodDetailData();
	}
}
