package com.sist.manager;
import java.util.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
					FoodVO fvo= new FoodVO();
					fvo.setCno(vo.getCno());
					/*
							 	<span class="title">
		                  			<h1 class="restaurant_name">몽중헌</h1>
		                    			<strong class="rate-point ">
		                      	<span>4.4</span>
		                    </strong>
		
		                  <p class="branch">방이점</p>
		                </span>
					 */
					Element name = doc2.selectFirst("span.title h1.restaurant_name");
					Element score = doc2.selectFirst("strong.rate-point span");
					Elements poster = doc2.select("figure.restaurant-photos-item img.center-croping");
					String image="";
					for(int j=0;j<poster.size();j++)
					{
						image+=poster.get(j).attr("src")+"^";
					}
					image=image.substring(0,image.lastIndexOf("^"));
					image=image.replace("&", "#");
					System.out.println("카테고리 번호 : "+vo.getCno());
					System.out.println("업체명 : "+name.text());
					System.out.println("평점 : "+score.text());
					System.out.println("이미지 : "+image);
					
					fvo.setName(name.text());
					fvo.setScore(Double.parseDouble(score.text()));
					fvo.setPoster(image);
					
					
					String addr="no",phone="no",type="no",price="no",parking="no",time="no",menu="no";
					Elements etc=doc2.select("table.info tr th");
					for(int a=0;a<etc.size();a++)
					{
						String ss=etc.get(a).text();
						if(ss.equals("주소"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							addr=e.text();
						}else if(ss.equals("전화번호"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							phone=e.text();
						}
						else if(ss.equals("음식 종류"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							type=e.text();
						}
						else if(ss.equals("가격대"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							price=e.text();
						}
						else if(ss.equals("주차"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							parking=e.text();
						}
						else if(ss.equals("영업시간"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							time=e.text();
						}
						else if(ss.equals("메뉴"))
						{
							Element e = doc2.select("table.info tr td").get(a);
							menu=e.text();
						}
					}
					System.out.println("주소 : "+addr);
					System.out.println("전화 : "+phone);
					System.out.println("음식 종류 : "+type);
					System.out.println("가격대 : "+price);
					System.out.println("주차 : "+parking);
					System.out.println("영업시간 : "+time);
					System.out.println("메뉴 : "+menu);
					fvo.setAddress(addr);
					fvo.setPhone(phone);
					fvo.setType(type);
					fvo.setPrice(price);
					fvo.setParking(parking);
					fvo.setTime(time);
					fvo.setMenu(menu);
					
					dao.foodDataInsert(fvo);
				}
			}
			System.out.println("저장 완료!!");
		}catch(Exception ex) {}
		
	}
	public static void main(String[] args) {
		DataCollection dc = new DataCollection();
		dc.foodDetailData();
	}
}
