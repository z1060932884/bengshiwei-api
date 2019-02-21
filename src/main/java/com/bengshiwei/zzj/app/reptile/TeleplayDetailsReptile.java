package com.bengshiwei.zzj.app.reptile;

import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.TelePlayUrl;
import com.bengshiwei.zzj.app.utils.Hib;
import com.bengshiwei.zzj.app.utils.ReptileUtils;
import org.hibernate.Session;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeleplayDetailsReptile implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    static List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieDetailsList("综艺片");

    @Override
    public void process(Page page) {

        Logger logger = Logger.getLogger("TeleplayDetailsReptile");
        logger.setLevel(Level.INFO);
        logger.info("爬取的电视剧详情----》" + page);
        List<String> content = page.getHtml().xpath("//div[@class='vodinfobox']//ul//li//span/text()").all();
        List<String> desc = page.getHtml().xpath("//div[@class='ibox playBox']//div[@class='vodplayinfo']/text()").all();
        String image = page.getHtml().xpath("//div[@class='vodImg']//img[@class='lazy']/@src").get();
        List<String> playUrl = page.getHtml().xpath("//div[@id='play_1']//ul//li/text()").all();

        System.out.println("导演----->" + content);
        System.out.println("desc----->" + desc);
        System.out.println("url----->" + playUrl);
        System.out.println("image----->" + image);

        List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieUrl(page.getUrl().toString().trim());
        if (movieDetailsModels != null && movieDetailsModels.size() > 0) {
            MovieDetailsModel movieDetailsModel = movieDetailsModels.get(0);
            movieDetailsModel.setDirector(content.get(1));

            movieDetailsModel.setRegion(content.get(4));
            try {
                movieDetailsModel.setActor(content.get(2));
            } catch (Exception e) {
                e.printStackTrace();
            }

            movieDetailsModel.setMovieDesc(desc.get(1));

            movieDetailsModel.setImg(image);
            movieDetailsModel.setPlayUrl("1");
            Set<TelePlayUrl> telePlayUrls = new HashSet<>();
            try {
                Session session = Hib.session();
                session.beginTransaction();
//            //保存电视剧的集数
                for (String url : playUrl) {
                    TelePlayUrl telePlayUrl = new TelePlayUrl();
                    telePlayUrl.setMovieDetailsModel(movieDetailsModel);
                    telePlayUrl.setPlayUrl(url);
                    telePlayUrls.add(telePlayUrl);
                    session.save(telePlayUrl);
                }


                session.saveOrUpdate(movieDetailsModel);
                session.getTransaction().commit();
                System.out.println("写入成功");
            } catch (Exception e) {
                System.out.println("写入失败" + e.getMessage());
            }
        }


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
//                Spider.create(new TeleplayDetailsReptile()).addUrl("http://www.zuidazyw.com/?m=vod-detail-id-54683.html").thread(5).run();
//
                for(MovieDetailsModel movieReptileModel : movieDetailsModels){
//                    System.out.println("url____------>"+movieReptileModel.getUrl());
                    if(movieReptileModel.getPlayUrl()==null){
                        Spider.create(new TeleplayDetailsReptile()).addUrl(movieReptileModel.getUrl()).thread(5).run();
                    }

                }

            }
        }, 1000);
    }
}
