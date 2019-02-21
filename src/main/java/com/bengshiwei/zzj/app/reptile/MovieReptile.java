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

        for (int i = 0; i < titles.size(); i++) {
            List<MovieDetailsModel> movieDetailsModels = ReptileUtils.getMovieUrl(urls.get(i));
            if (movieDetailsModels != null && movieDetailsModels.size() > 0) {
                System.out.println("写入失败,数据库已存储");
            } else {
                if(titles.get(i)!=null&&!titles.get(i).equals("")){
                    MovieDetailsModel testReptile = new MovieDetailsModel();
//                    testReptile.setImg(imgs.get(i));
                    testReptile.setTitle(titles.get(i));
                    testReptile.setUrl(urls.get(i));
                    testReptile.setType(type);
                    testReptile.setMovieType(movieType.get(i));
//                    testReptile.setDefinition(definitions.get(i));
                    testReptile.setUpdateTime(times.get(i));
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
                    System.out.println("标题为空");
                }
            }
        }
        System.out.println("type_____------->"+type);
        System.out.println("titles--->"+titles+"抓取数量----》"+titles.size());
        System.out.println("urls----->"+urls+"抓取数量----》"+urls.size());
//        System.out.println("imgs----->"+imgs+"抓取数量----》"+imgs.size());
        System.out.println("movieType----->"+movieType+"抓取数量----》"+movieType.size());
        System.out.println("times----->"+times+"抓取数量----》"+times.size());
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
                Spider.create(new MovieReptile()).addUrl("http://www.zuidazyw.com/?m=vod-type-id-3-pg-2.html").thread(5).run();
            }
        }, 1000);
    }
}
