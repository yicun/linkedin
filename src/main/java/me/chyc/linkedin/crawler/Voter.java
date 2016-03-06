package me.chyc.linkedin.crawler;

import me.chyc.entity.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by yicun.chen on 7/23/15.
 */
public class Voter {
    WebDriver webDriver;

    public Voter() {

    }

    public Voter(String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        this.webDriver.get("http://www.jsagri.gov.cn/survey/xjrw/zmsy.htm");

        this.webDriver.switchTo();
        //输入用户名
        WebElement select = this.webDriver.findElement(By.id("Checkbox14"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", select);
        select.click();
        if (select.isSelected()) {
            System.out.println("selected");
        }
        //点击登录按钮
        WebElement submit = this.webDriver.findElement(By.id("Submit1"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", submit);
        this.webDriver.findElement(By.id("Submit1")).click();
        this.webDriver.switchTo().defaultContent();
        try {
            while (true) {
                Thread.sleep(500L);
                if (!this.webDriver.getCurrentUrl().startsWith("http://www.jsagri.gov.cn:8080/cms/vote/viewvote_zmsy.jsp")) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("voted");
    }

    public Voter(String driverPath, String host, int port) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=socks5://" + host + ":" + port);
        WebDriver driver = new ChromeDriver(options);
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.webDriver.get("http://www.jsagri.gov.cn/survey/xjrw/zmsy.htm");

        this.webDriver.switchTo();
        //输入用户名
        WebElement select = this.webDriver.findElement(By.id("Checkbox14"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", select);
        select.click();
        if (select.isSelected()) {
            System.out.println("selected");
        }
        //点击登录按钮
        WebElement submit = this.webDriver.findElement(By.id("Submit1"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", submit);
        this.webDriver.findElement(By.id("Submit1")).click();
        this.webDriver.switchTo().defaultContent();
        try {
            Thread.sleep(1000L);
            if (this.webDriver.getCurrentUrl().startsWith("http://www.jsagri.gov.cn:8080/cms/vote/viewvote_zmsy.jsp"))
                System.out.println("voted");
            else
                System.out.println("not voted");
            String html = this.webDriver.getPageSource();
            System.out.println(html);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(String driverPath, String host, int port) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=http://" + host + ":" + port);
        this.webDriver = new ChromeDriver(options);
    }

    public String[] ids = {"Checkbox4",
            "Checkbox7",
            "Checkbox12",
            "Checkbox19",
            "Checkbox8",
            "Checkbox3",
            "Checkbox20",
            "Checkbox10",
            "Checkbox2"};

    public String vote() {
        this.webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.webDriver.get("http://www.jsagri.gov.cn/survey/xjrw/zmsy.htm");

        this.webDriver.switchTo();
        //输入用户名
        WebElement select = this.webDriver.findElement(By.id("Checkbox14"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", select);
        select.click();
        if (select.isSelected()) {
            System.out.println(select.getAttribute("id") + "\tselected");
        }

        Random r = new Random(System.currentTimeMillis());
        int count = 2;
        for (String id : ids) {
//            System.out.println(id);
            if (r.nextBoolean()) {
                WebElement select2 = this.webDriver.findElement(By.id(id));
                ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", select2);
                select2.click();
                if (select2.isSelected()) {
                    System.out.println(select2.getAttribute("id") + "\tselected");
                    count--;
                }
            }
            if (count == 0)
                break;
        }

        //点击登录按钮
        WebElement submit = this.webDriver.findElement(By.id("Submit1"));
        ((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView();", submit);
        this.webDriver.findElement(By.id("Submit1")).click();
        this.webDriver.switchTo().defaultContent();
        String html = null;
        try {
            while (true) {
                Thread.sleep(1000L);
                if (this.webDriver.getCurrentUrl().startsWith("http://www.jsagri.gov.cn:8080/cms/vote/viewvote_zmsy.jsp")) {
                    System.out.println("voted");
                    break;
                }
            }
            html = this.webDriver.getPageSource();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return html;
    }


    public void close() {
        this.webDriver.close();
        this.webDriver.quit();
    }

    /*
    *  <tr data-id="2364682" data-type="anonymous">
      <td class="tcenter">&nbsp;1&nbsp;</td>
      <td>124.14.12.170</td>
      <td><script>document.write((41914^duck)+21928);</script></td>
            <td>
	<img border="0" width="16" height="11" alt="中国" src="/view/ensign/cn.png" />&nbsp;
        <a href="/area/short/name/cn.html" title="中国上海市代理服务器">中国</a>
        <em>(<a href="/area/city/name/上海市.html">上海市)</a></em>      </td>
            <td class="tcenter type"><a href="/area/short/name/cn/type/anonymous.html">anonymous</a></td>
      <td class="tcenter">
		<span class="zt3">繁忙</span>	      </td>
      <td class="cs"><a href="javascript:;" name="2364682">测试</a></td>
    </tr>
    * */
    public static List<Pair<String, Integer>> Getter(String url) throws Exception {
        List<Pair<String, Integer>> proxies = new ArrayList<Pair<String, Integer>>();
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
//        String url = "http://pachong.org";
        if (url == null)
            url = "http://pachong.org";
        String html = getWebPage(url, "UTF-8");
//        System.out.println(html);
        Element element = Jsoup.parse(html);
        String script = "";
        for (Element scriptItem : element.select("script[type=text/javascript]")) {
            String data = scriptItem.data();
            if (data != null && data.length() != 0 && !data.startsWith("$")) {
                script = data;
                break;
            }
        }
//        System.out.println(script);
        for (Element tr : element.select("tbody").select("tr")) {
            Elements tds = tr.select("td");
            if (tds == null || tds.size() < 3)
                continue;
            String host = tds.get(1).text();
            if (host.matches("[\\d]+\\.[\\d]+\\.+[\\d]+\\.[\\d]+")) {
                String script2 = tds.get(2).select("script").first().data().replaceAll("document.write", "port = ");
                se.eval(script + " " + script2);
                int port = Double.valueOf(se.get("port").toString()).intValue();
//                System.out.println(host + "\t" + port);
                proxies.add(new Pair<String, Integer>(host, port));
            }
        }

        return proxies;
    }

    public static String getWebPage(String url, String encode) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpget);
        StringBuilder sb = new StringBuilder();
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), encode));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            br.close();
        }
        response.close();
        httpClient.close();
        return sb.toString();
    }

    public List<Pair<String, String>> getVotes(String html) {
        Document document = Jsoup.parse(html);
        Element tbody = document.getElementsByTag("tbody").first();
        for (Element tr : tbody.getElementsByTag("tr")) {
            System.out.println(tr.text());
        }
        Element center = document.getElementsByTag("center").first().getElementsByTag("font").first();
        System.out.println(center.text());
        return null;
    }

    public static void main(String args[]) throws Exception {
        String chromeDriverPath = "/Users/chyc/Workspaces/Mine/linkedin/lib/chromedriver";

        List<Pair<String, Integer>> proxies = new ArrayList<Pair<String, Integer>>();
        proxies.add(new Pair<String, Integer>("113.69.86.221", 9000));
//                proxies= Voter.Getter("http://pachong.org");
        System.out.println(proxies.size());

        for (int index = 0; index < proxies.size(); index++) {
            Pair<String, Integer> proxy = proxies.get(index);
            System.out.println("No." + index + "\t" + proxy.toString());
            String host = proxy.value1;
            int port = proxy.value2;
            Voter voter = new Voter();
            voter.init(chromeDriverPath, host, port);
            String html = voter.vote();
            voter.close();
            voter.getVotes(html);
            break;
        }
        System.exit(1);
    }
}
