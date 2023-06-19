
package com.sist.manager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonParsingExample {
    public static void main(String[] args) {
        try {
            String[] urls = new String[50];
            for (int i = 0; i < urls.length; i++) {
                urls[i] = "http://api.visitjeju.net/vsjApi/contents/searchList?apiKey=diqyi0iva82ccjry&locale=kr&page=" + (i + 1);
            }

            int count = 1;

            for (String apiUrl : urls) {
                URL url = new URL(apiUrl);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
                reader.close();

                String jsonData = jsonBuilder.toString();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

                JSONArray dataArray = (JSONArray) jsonObject.get("items");

                if (dataArray != null) {
                    for (Object item : dataArray) {
                        JSONObject dataObj = (JSONObject) item;
                        String title = (String) dataObj.get("title");
                        String tag = (String) dataObj.get("alltag");
                        String contentsid = (String) dataObj.get("contentsid");
                        String addr = (String) dataObj.get("address");
                        String road = (String) dataObj.get("roadaddress");
                        String introduction = (String) dataObj.get("introduction");
                        String phone = (String) dataObj.get("phoneno");
                        if(phone==null)
                        	phone="--";   
                        JSONObject constendscdObj = (JSONObject) dataObj.get("contentscd");
                        String label = (String) constendscdObj.get("label");
                        JSONObject repphotoObj = (JSONObject) dataObj.get("repPhoto");
                        JSONObject region1cdObj = (JSONObject) dataObj.get("region1cd");
                        String loc="";
                        if(region1cdObj==null)
                        	loc=" ";
                        else
                        	loc = (String) region1cdObj.get("label");
                        
                        if (repphotoObj == null) {
                            continue; // Skip this item if repphotoObj is null
                        }
                        JSONObject photoidObj = (JSONObject) repphotoObj.get("photoid");
                        
                        

                        if ("관광지".equals(label) && photoidObj != null) {
                            String poster = (String) photoidObj.get("imgpath");
                            if (poster != null) {
                            	
                            	System.out.println(count+")");
                                System.out.println("이름 : " + title);
                                System.out.println("태그 : "+tag);
                                System.out.println("소개 : "+introduction);
                                System.out.println("지역 : "+loc); 
                                System.out.println("주소 : "+addr);
                                System.out.println("도로명 주소 : "+road);
                                System.out.println("전화 : "+phone);
                                System.out.println("Poster : " + poster);
                                String detail = "https://visitjeju.net/kr/detail/view?contentsid="+contentsid;
                                try
                                {
                                	Document doc= Jsoup.connect(detail).get();
                                	Element detailinfo = doc.selectFirst("meta[name=description]");
                                	String info = detailinfo.attr("content");
                                	System.out.println(detail);
                                	if(info==null)
                                		info="상세 설명 없음";
                                	 System.out.println(info);
                                	
                                }catch(Exception ex) {}
                               
                                count++;
                                System.out.println("============================================");
                            }
                        }
                    }
                } else {
                    System.out.println("No data found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}