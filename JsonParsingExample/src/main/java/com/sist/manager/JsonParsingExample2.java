package com.sist.manager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonParsingExample2 {
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
                        JSONObject constendscdObj = (JSONObject) dataObj.get("contentscd");
                        String label = (String) constendscdObj.get("label");
                        JSONObject repphotoObj = (JSONObject) dataObj.get("repPhoto");
                        JSONObject region1cdObj = (JSONObject) dataObj.get("region1cd");
                        String regionLabel="";
                        if(region1cdObj==null)
                        	regionLabel=" ";
                        else
                        	regionLabel = (String) region1cdObj.get("label");
                        
                        if (repphotoObj == null) {
                            continue; // Skip this item if repphotoObj is null
                        }
                        
                        JSONObject photoidObj = (JSONObject) repphotoObj.get("photoid");

                        if (!"테마여행".equals(label) && photoidObj != null) {
                            String imgpath = (String) photoidObj.get("imgpath");
                            if (imgpath != null) {
                                System.out.println(count + ". Title: " + title);
                                System.out.println("태그 : "+tag);
                                System.out.println("Poster : " + imgpath);
                                System.out.println("지역 : "+regionLabel); 
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