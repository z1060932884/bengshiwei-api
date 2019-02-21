package com.bengshiwei.zzj.app.reptile;

import com.bengshiwei.zzj.app.utils.ReptileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by JamesZhang on 2018/5/5.
 */
public class SinaYuLeNewsReptile implements PageProcessor {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public void process(Page page) {
        logger.warning("爬取新浪娱乐的新闻-----》"+page);
        List<String> titles = page.getHtml().xpath("//div[@class='ty-card-r']//h3[@class='ty-card-tt']//a/text()").all();
        List<String> imgs = page.getHtml().xpath("//div[@class='ty-card-l']//div[@class='ty-card-thumb-w']//a/img/@src").all();
        List<String> urls = page.getHtml().xpath("//div[@class='ty-card-r']//h3[@class='ty-card-tt']//a/@href").all();
//        List<String> sources = page.getHtml().xpath("//div[@class='ty-card-r']//p[@class='ty-card-tip2 clearfix']//span[@class='ty-card-tag']/a/text()").all();
        logger.warning("title--->"+titles+"\n数量--->"+titles.size());
        logger.warning("imgs--->"+imgs+"\n数量--->"+imgs.size());
        logger.warning("urls--->"+urls+"\n数量--->"+urls.size());
//        logger.warning("sources--->"+sources+"\n数量--->"+sources.size());
        ReptileUtils.saveNews(titles,imgs,urls,"yule");
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spider.create(new SinaYuLeNewsReptile())
                        .addUrl("http://ent.sina.com.cn/")
                        .setDownloader(new SeleniumDownloader("C:\\Users\\JamesZhang\\Desktop\\chromedriver\\chromedriver.exe")).thread(5).run();
            }
        }, 1000);
// 第一步： 设置chromedriver地址。一定要指定驱动的位置。
   /*     System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\JamesZhang\\Desktop\\reptile\\chromedriver.exe");
        // 第二步：初始化驱动
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // 第三步：获取目标网页
        driver.get("http://ent.sina.com.cn/");

        *//*String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
        Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            if (currentWindow == it.next()) {
                continue;
            }
            driver = driver.switchTo().window(it.next());// 切换到新窗口


            driver.switchTo().window(currentWindow);//回到原来页面
            // 第四步：解析。以下就可以进行解了。使用webMagic、jsoup等进行必要的解析。
            System.out.println("Page title is1111: " + driver.getTitle());
            System.out.println("Page title111 is: " + driver.getPageSource());
        }*//*
        // 第四步：解析。以下就可以进行解了。使用webMagic、jsoup等进行必要的解析。
        System.out.println("Page title is2222: " + driver.getTitle());
        System.out.println("Page title is2222: " + driver.getPageSource());*/

    }
}
