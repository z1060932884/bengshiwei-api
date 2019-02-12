package com.bengshiwei.zzj.app.reptile;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.utils.Hib;
import com.bengshiwei.zzj.app.utils.ReptileUtils;
import org.hibernate.Session;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShanDongNewsReptile implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

        Logger logger = Logger.getLogger("ShanDongNewsReptile");
        logger.setLevel(Level.INFO);
        logger.info("爬取的山东新闻----》" + page);
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<String> imgs = new ArrayList<>();
//        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
//        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());

        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());


        System.out.println("titles--->"+titles+"抓取数量----》"+titles.size());
        System.out.println("urls----->"+urls+"抓取数量----》"+urls.size());
        System.out.println("imgs----->"+imgs+"抓取数量----》"+imgs.size());

        for (int i = 0; i < titles.size(); i++) {
            List<NewsReptileModel> testReptileModels = ReptileUtils.getUrl(urls.get(i));
            if (testReptileModels != null && testReptileModels.size() > 0) {
                System.out.println("写入失败,数据库已存储");
            } else {
                if(titles.get(i)!=null&&!titles.get(i).equals("")){
                    NewsReptile testReptile = new NewsReptile();
                    testReptile.setImg(imgs.get(i));
                    testReptile.setTitle(titles.get(i));
                    testReptile.setUrl(urls.get(i));
                    testReptile.setType("shandong");
                    Session session = Hib.session();
                    session.beginTransaction();
                    try {
                        session.save(testReptile);
                        System.out.println("写入成功");
                    } catch (Exception e) {
                        System.out.println("写入失败" + e.getMessage());
                    }
                    session.getTransaction().commit();
                }else {
                    logger.info("标题为空");
                }
            }
        }

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
                Spider.create(new ShanDongNewsReptile()).addUrl("http://sd.sina.com.cn/").thread(5).run();
            }
        }, 1000);
    }
}
