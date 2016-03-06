package me.chyc.linkedin.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by yicun.chen on 7/1/15.
 */
public class LinkedinCrawler {
    WebDriver webDriver;

    public LinkedinCrawler(String username, String password) {
//        WebDriver webDriver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/Users/chyc/Workspaces/Mine/linkedin/lib/chromedriver");
        this.webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://www.linkedin.com/uas/login");

        //输入用户名
        webDriver.findElement(By.id("session_key-login")).clear();
        webDriver.findElement(By.id("session_key-login")).sendKeys(username);
        //输入密码
        webDriver.findElement(By.id("session_password-login")).clear();
        webDriver.findElement(By.id("session_password-login")).sendKeys(password);

        //点击登录按钮
        webDriver.findElement(By.id("btn-primary")).click();
        webDriver.switchTo().defaultContent();
        try {
            while (true) {
                Thread.sleep(500L);
                if (!webDriver.getCurrentUrl().startsWith("https://www.linkedin.com/uas/login")) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LinkedinCrawler(String username, String password, String driverPath) {
//        WebDriver webDriver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", driverPath);
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.webDriver.get("https://www.linkedin.com/uas/login");

        //输入用户名
        this.webDriver.findElement(By.id("session_key-login")).clear();
        this.webDriver.findElement(By.id("session_key-login")).sendKeys(username);
        //输入密码
        this.webDriver.findElement(By.id("session_password-login")).clear();
        this.webDriver.findElement(By.id("session_password-login")).sendKeys(password);

        //点击登录按钮
        this.webDriver.findElement(By.id("btn-primary")).click();
        this.webDriver.switchTo().defaultContent();
        try {
            while (true) {
                Thread.sleep(500L);
                if (!this.webDriver.getCurrentUrl().startsWith("https://www.linkedin.com/uas/login")) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String get(String pageUrl) {
        this.webDriver.get(pageUrl);
        this.webDriver.switchTo().defaultContent();
        try {
            Thread.sleep(1000L);
            return this.webDriver.getPageSource();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String search(String pageUrl, String keywords) {
        this.webDriver.get(pageUrl);
        //输入关键词
        this.webDriver.findElement(By.id("main-search-box")).clear();
        this.webDriver.findElement(By.id("main-search-box")).sendKeys(keywords);
        //点击搜索
        this.webDriver.findElement(By.name("search")).click();
        this.webDriver.switchTo().defaultContent();
        try {
            Thread.sleep(10000L);
//            while (true) {
//                Thread.sleep(500L);
//                if (!this.webDriver.getCurrentUrl().contains(keywords)) {
//                    break;
//                }
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.webDriver.getPageSource();
    }

    public void close() {
        this.webDriver.close();
    }

    public static void main(String args[]) {
        String username = "chyc6278@gmail.com";
        String password = "chyc6278";
        String chromeDriverPath = "/Users/chyc/Workspaces/Mine/linkedin/lib/chromedriver";
        String pageUrl = "http://www.linkedin.com/profile/view?id=2466569";
        String searchUrl = "http://www.linkedin.com/vsearch/f";

        LinkedinCrawler linkedinCrawler = new LinkedinCrawler(username, password, chromeDriverPath);
//        String page = linkedinCrawler.get(pageUrl);
//        System.out.println(page);
//        String keywords = "Coca-Cola";
//        String page = linkedinCrawler.search(searchUrl, keywords);
//        System.out.println(page);
        String url = "http://www.linkedin.com/vsearch/p?title=Marketing&company=Coca-Cola&openAdvancedForm=true&titleScope=CP&companyScope=CP&locationType=Y&rsid=959697691435835692394&orig=MDYS&page_num=2&pt=people";
        String page = linkedinCrawler.get(url);
        System.out.println(page);
    }
}
