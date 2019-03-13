package com.bengshiwei.zzj.app.reptile.zuidam3u8;

import com.bengshiwei.zzj.app.bean.db.M3U8MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.M3U8TelePlayUrl;
import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.TelePlayUrl;
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

public class M3U8TeleplayDetailsReptile implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    static List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieDetailsList("动漫片");

    @Override
    public void process(Page page) {

        Logger logger = Logger.getLogger("M3U8TeleplayDetailsReptile");
        logger.setLevel(Level.INFO);
        logger.info("爬取的电视剧详情----》" + page);
        List<String> content = page.getHtml().xpath("//div[@class='vodinfobox']//ul//li//span/text()").all();
        List<String> desc = page.getHtml().xpath("//div[@class='ibox playBox']//div[@class='vodplayinfo']/text()").all();
        String image = page.getHtml().xpath("//div[@class='vodImg']//img[@class='lazy']/@src").get();
        List<String> playUrl = page.getHtml().xpath("//div[@id='play_1']//ul//li/text()").all();
//        List<String> playUrl2 = page.getHtml().xpath("//div[@id='play_2']//ul//li/text()").all();

        System.out.println("导演----->" + content);
        System.out.println("desc----->" + desc);
        System.out.println("url----->" + playUrl);
//        System.out.println("url2----->" + playUrl2);
        System.out.println("image----->" + image);

        List<M3U8MovieDetailsModel> movieDetailsModels = ReptileUtils.getM3U8MovieUrl(page.getUrl().toString().trim());
        if (movieDetailsModels != null && movieDetailsModels.size() > 0) {
            M3U8MovieDetailsModel movieDetailsModel = movieDetailsModels.get(0);
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
            movieDetailsModel.setPlayUrl2("1");

            List<M3U8TelePlayUrl> telePlayUrls1 = ReptileUtils.geM3u8TeleplayUrl(movieDetailsModel.getId());
//                List<TelePlayUrl2> telePlayUrls2 = ReptileUtils.geTeleplayUrl2(movieDetailsModel.getId());
//            //保存电视剧的集数1
            if (telePlayUrls1.size() != playUrl.size()) {
                Session session = Hib.session();
                session.beginTransaction();
                try {


                    for (int i = 0; i < playUrl.size(); i++) {

                        String url = playUrl.get(i);
                        if (i > telePlayUrls1.size() - 1) {
                            M3U8TelePlayUrl telePlayUrl = new M3U8TelePlayUrl();
                            telePlayUrl.setMovieDetailsModel(movieDetailsModel);
                            telePlayUrl.setPlayUrl(url);
//                        telePlayUrls.add(telePlayUrl);
                            session.save(telePlayUrl);
                        }
                    }

                    session.saveOrUpdate(movieDetailsModel);
                    session.getTransaction().commit();
                    System.out.println("写入成功");
                } catch (Exception e) {
                    System.out.println("写入失败" + e.getMessage());
                    //如果失败情况下事件回滚
                    session.getTransaction().rollback();
                }
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
                Spider.create(new M3U8TeleplayDetailsReptile()).addUrl("http://zuidazy.net/?m=vod-detail-id-53253.html").thread(5).run();
//
//                for(MovieDetailsModel movieReptileModel : movieDetailsModels){
////                    System.out.println("url____------>"+movieReptileModel.getUrl());
//                    if(movieReptileModel.getPlayUrl()==null){
//                        Spider.create(new TeleplayDetailsReptile()).addUrl(movieReptileModel.getUrl()).thread(5).run();
//                    }
//
//                }

            }
        }, 1000);
    }
}
