package com.sist.manager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class DataCollectionTest2 {
    public static void main(String[] args) {
        // ChromeDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\SIST\\Desktop\\chromedriver.exe");

        // ChromeDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver();

        try {
            String url = "https://planner.jeju.com/plans/plans_list.html?mode=theme&agt=jeju";

            // 웹 사이트 로드
            driver.get(url);

            // 웹 페이지에서 필요한 데이터 추출
            List<WebElement> titleElements = driver.findElements(By.className("pl_list_subject"));
            int count = 0;
            // 제목 출력
            for (WebElement titleElement : titleElements) {
                String title = titleElement.getText();
                	System.out.println(count);
                    System.out.println("Title: " + title);
                    count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
    }
}