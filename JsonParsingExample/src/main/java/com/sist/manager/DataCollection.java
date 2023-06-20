package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DataCollection {
	public void courseCategoryData() // 카테고리
	{
		try
		{
			String[] urls = new String[14];
			int count = 0;
            for (int i = 0; i < urls.length; i++)
            {
	            urls[i] = "https://visitjeju.net/kr/recommendTour/recommendScheduleList?menuId=DOM_000002000000000241&cate1cd=cate0000001350&page=" + (i + 1);
				Document doc = Jsoup.connect(urls[i]).get();
				Elements title = doc.select("p.s_tit");
				Elements theme = doc.select("p.s_theme");
				Elements hash = doc.select("p.item_tag");
				Elements poster = doc.select("dt.item_top a img");
				Elements id = doc.select("dt.item_top a");
				for(int j=0;j<title.size();j++)
				{
					if(!poster.get(j).attr("src").equals("/image"))
					{
						
						System.out.println(count);
						System.out.println("제목 : "+title.get(j).text());
						System.out.println("테마 : "+theme.get(j).text());
						System.out.println("해쉬태그 : "+hash.get(j).text());
						System.out.println("이미지 : "+poster.get(j).attr("src"));
						System.out.println(id.get(j).attr("data-tourid"));
						System.out.println("==============================================");
						count++;
					}
					
				}
            }
            
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataCollection dc = new DataCollection();
		dc.courseCategoryData();
	}

}
