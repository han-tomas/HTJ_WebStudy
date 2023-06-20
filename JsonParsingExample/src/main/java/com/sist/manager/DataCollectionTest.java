package com.sist.manager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DataCollectionTest {
    public static void main(String[] args) {
        String[] id = { "3131", "3854", "7119", "3017", "7101", "7095", "7115", "17567", "7111", "17569", "3018", "9672", "3046", "11864", "10490", "7098", "7087", "10060", "10540", "10227", "10058", "7113", "11850", "7093", "7102", "7114", "7104", "10121", "11371", "7089", "10317", "11887", "11415", "9554", "10194", "17566", "7109", "10205", "11483", "10346", "7110", "7112", "7091", "11546", "17565", "7092", "10374", "10097", "11651", "7094", "17242", "17244", "17564", "17245", "17243", "17233", "9199", "17563", "17543", "17562", "9560" };
        String[] urls = new String[id.length];

        // API 키
        String apiKey = "diqyi0iva82ccjry"; // TODO: API 키로 교체

        for (int i = 0; i < id.length; i++) {
            urls[i] = "https://api.visitjeju.net/api/node/tourschedule/read.json?_siteId=jejuavj&locale=kr&device=pc&tourid=" + id[i];

            try {
                // JSON 파일을 가져옴
                String jsonString = readUrl(urls[i], apiKey);

                // JSON 파싱
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

                // item 배열 가져오기
                JSONArray itemArray = (JSONArray) jsonObject.get("item");

                // label 값 출력
                StringBuilder labels = new StringBuilder();
                for (Object item : itemArray) {
                    try {
                        JSONObject itemObject = (JSONObject) item;
                        String label = (String) itemObject.get("label");
                        labels.append(label).append("-");
                    } catch (ClassCastException e) {
                        // label 값이 없는 경우, 해당 아이템은 건너뜀
                    }
                }

                // 마지막 '-' 문자 제거하고 출력
                if (labels.length() > 0) {
                    labels.setLength(labels.length() - 1);
                    System.out.println(labels.toString());
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readUrl(String urlString, String apiKey) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 인증 정보 추가
        String auth = "Basic " + Base64.getEncoder().encodeToString((":" + apiKey).getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", auth);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        return sb.toString();
    }
}