package me.chyc.linkedin.crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yicun.chen on 7/2/15.
 */
public class LinkedinLoginDemo {
//以下方法获取登录淘宝成功后的cookie

    public static java.lang.String click(String username, String password) {
//        WebDriver webDriver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/Users/chyc/Workspaces/Mine/linkedin/lib/chromedriver");
        WebDriver webDriver = new ChromeDriver();
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
            //不停的检测，一旦当前页面URL不是登录页面URL，就说明浏览器已经进行了跳转
            while (true) {
                Thread.sleep(500L);
                if (!webDriver.getCurrentUrl().startsWith("https://www.linkedin.com/uas/login")) {
                    break;
                }
            }
//            System.out.println(webDriver.getPageSource());
            webDriver.get("http://www.linkedin.com/profile/view?id=2466569");
            webDriver.switchTo().defaultContent();
            Thread.sleep(10000L);
            System.out.println(webDriver.getPageSource());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取cookie，上面一跳出循环我认为就登录成功了，当然上面的判断不太严格，可以再进行修改
        Set<Cookie> cookies = webDriver.manage().getCookies();
        String cookieStr = "";
        for (Cookie cookie : cookies) {
            cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";
        }

        //退出，关闭浏览器
        webDriver.quit();
        return cookieStr;
    }

    public static void main(String args[]) {
        String username = "chyc6278@gmail.com";
        String password = "chyc6278";
        String chromeDriverPath = "/Users/chyc/Workspaces/Mine/linkedin/lib/chromedriver";
        LinkedinLoginDemo.click(username, password);
    }
}
