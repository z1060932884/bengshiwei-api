package com.bengshiwei.zzj.app.reptile;

import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.MovieReptileModel;
import com.bengshiwei.zzj.app.utils.Hib;
import com.bengshiwei.zzj.app.utils.ReptileUtils;
import org.hibernate.Session;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoviePlayUrlReptile implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    //获取列表中最新电影的数据集合
    static List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieDetailsList("");
    @Override
    public void process(Page page) {

        Logger logger = Logger.getLogger("MoviePlayUrlReptile");
        logger.setLevel(Level.INFO);
        logger.info("爬取的影视url----》" + page);

        String url = page.getHtml().xpath("//div[@class='player']//script/text()").get();

        System.out.println("url----->"+url);


//        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
//        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());

//        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
//        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());


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
                Spider.create(new MoviePlayUrlReptile())
                        .addUrl("http://www.zuidazyw.com/?m=vod-detail-id-53913.html")
//                        .setDownloader(new SeleniumDownloader("C:\\Users\\JamesZhang\\Desktop\\chromedriver\\chromedriver.exe"))
                        .thread(5).run();
//
//                for(MovieDetailsModel movieReptileModel : movieDetailsModels){
////                    System.out.println("url____------>"+movieReptileModel.getUrl());
//                    Spider.create(new MoviePlayUrlReptile()).addUrl(movieReptileModel.getUrl()).thread(5).run();
//                }

            }
        }, 1000);
    }
}
