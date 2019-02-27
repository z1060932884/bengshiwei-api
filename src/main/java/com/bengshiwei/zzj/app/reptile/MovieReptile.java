package com.bengshiwei.zzj.app.reptile;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.MovieReptileModel;
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

public class MovieReptile implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

        Logger logger = Logger.getLogger("MovieReptile");
        logger.setLevel(Level.INFO);
        logger.info("爬取的影视----》" + page);
        List<String> titles = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<String> movieType = new ArrayList<>();
//        List<String> imgs = new ArrayList<>();
        List<String> times = new ArrayList<>();
//        List<String> definitions = new ArrayList<>();

        String type = page.getHtml().xpath("//div[@class='container']//div[@class='nvc']//dd/a/text()").all().get(1);
        //最大资源网
        titles.addAll(page.getHtml().xpath("//div[@class='xing_vb']//li//span[@class='xing_vb4']//a/text()").all());
        urls.addAll(page.getHtml().xpath("//div[@class='xing_vb']//li//span[@class='xing_vb4']//a/@href").all());
        movieType.addAll(page.getHtml().xpath("//div[@class='xing_vb']//li//span[@class='xing_vb5']/text()").all());
        times.addAll(page.getHtml().xpath("//div[@class='xing_vb']//li//span[@class='xing_vb6']/text()").all());
         //热门推荐
//        titles.addAll(page.getHtml().xpath("//div[@class='box-item']//a[@class='item-link']/@title").all());
//        urls.addAll(page.getHtml().xpath("//div[@class='box-item']//a[@class='item-link']/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='box-item']//a[@class='item-link']//img/@src").all());
//        times.addAll(page.getHtml().xpath("//div[@class='box-item']//div[@class='meta']//em//strong//span/text()").all());
//        definitions.addAll(page.getHtml().xpath("//div[@class='box-item']//a[@class='item-link']//button/text()").all());
//        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
//        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());

//        titles.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@alt").all());
//        urls .addAll(page.getHtml().xpath("//div[@class='news-img']//a/@href").all());
//        imgs.addAll(page.getHtml().xpath("//div[@class='news-img']//a/img/@src").all());
        MovieDetailsModel movieDetailsModel = null;
        for (int i = 0; i < titles.size(); i++) {
            List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieUrl(urls.get(i));
            if (movieDetailsModels != null && movieDetailsModels.size() > 0) {
                movieDetailsModel =  movieDetailsModels.get(0);
                System.out.println("更新数据");
            } else {
                if(titles.get(i)!=null&&!titles.get(i).equals("")){
                    movieDetailsModel = new MovieDetailsModel();
//                    testReptile.setImg(imgs.get(i));
                    movieDetailsModel.setUrl(urls.get(i));
                    movieDetailsModel.setType(type);
                    movieDetailsModel.setMovieType(movieType.get(i));

                }else {
                    System.out.println("标题为空");
                }
            }
            movieDetailsModel.setTitle(titles.get(i));
            movieDetailsModel.setUpdateTime(times.get(i));
            Session session = Hib.session();
            session.beginTransaction();
            try {
                session.saveOrUpdate(movieDetailsModel);
                System.out.println("写入成功");
            } catch (Exception e) {
                System.out.println("写入失败" + e.getMessage());
            }
            session.getTransaction().commit();
            Spider.create(new TeleplayDetailsReptile()).addUrl(urls.get(i)).thread(5).run();
        }
//        System.out.println("type_____------->"+type);
//        System.out.println("titles--->"+titles+"抓取数量----》"+titles.size());
//        System.out.println("urls----->"+urls+"抓取数量----》"+urls.size());
////        System.out.println("imgs----->"+imgs+"抓取数量----》"+imgs.size());
//        System.out.println("movieType----->"+movieType+"抓取数量----》"+movieType.size());
//        System.out.println("times----->"+times+"抓取数量----》"+times.size());
//        System.out.println("definitions----->"+definitions+"抓取数量----》"+definitions.size());


    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        //http://www.zuidazyw.com/?m=vod-type-id-1-pg-4.html  电影
        //http://www.zuidazyw.com/?m=vod-type-id-2.html  连续剧
        //http://www.zuidazyw.com/?m=vod-type-id-3.html 综艺
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spider.create(new MovieReptile()).addUrl("http://zuidazy.net/?m=vod-type-id-4.html").thread(5).run();
//               for(int i =2;i<6;i++){
//                   Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-4-pg-"+i+".html").thread(5).run();
//               }
            }
        }, 1000);
    }
}
