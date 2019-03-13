package com.bengshiwei.zzj.app.utils;


import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.db.*;
import org.hibernate.Session;

import java.util.List;

public class ReptileUtils {

    /**
     * 通过url查找当前是否已经存储数据库
     *
     * @param url
     * @return
     */
    public static List<NewsReptileModel> getUrl(String url) {
        return Hib.query(session -> {
            return (List<NewsReptileModel>) session
                    .createQuery("from NewsReptile where url=:url")
                    .setParameter("url", url)
                    .list();
        });
    }
    /**
     * 通过url查找当前是否已经存储数据库
     *
     * @param url
     * @return
     */
    public static List<MovieDetailsModel> getMovieUrl(String url) {
        return Hib.query(session -> {
            return (List<MovieDetailsModel>) session
                    .createQuery("from MovieDetailsModel where url=:url")
                    .setParameter("url", url)
                    .list();
        });
    }
    /**
     * 通过url查找当前是否已经存储数据库
     *
     * @param url
     * @return
     */
    public static List<M3U8MovieDetailsModel> getM3U8MovieUrl(String url) {
        return Hib.query(session -> {
            return (List<M3U8MovieDetailsModel>) session
                    .createQuery("from M3U8MovieDetailsModel where url=:url")
                    .setParameter("url", url)
                    .list();
        });
    }

    /**
     *查询影视列表
     * @return
     */
    public static List<MovieReptileModel> getMovieList(String type){
        //根据时间排序查询
        return Hib.query(session -> session.createQuery("from MovieReptileModel where type=:userid order by updateAt desc")
                .setParameter("userid",type)
                .list());
    }
    /**
     *查询影视列表
     * @return
     */
    public static List<MovieDetailsModel> getMovieDetailsList(String type){
        //根据时间排序查询
        return Hib.query(session -> session.createQuery("from MovieDetailsModel where type=:userid order by updateAt desc")
                .setParameter("userid",type)
                .list());
    }
    /**
     *查询影视url
     * @return
     */
    public static List<TelePlayUrl> geTeleplayUrl(String movieId){
        //根据时间排序查询
        return Hib.query(session ->{
            return (List<TelePlayUrl>)session.createQuery("from TelePlayUrl where movieId=:movieId")
                .setParameter("movieId",movieId)
                .list();
            });
    }
    /**
     *查询影视url
     * @return
     */
    public static List<M3U8TelePlayUrl> geM3u8TeleplayUrl(String movieId){
        //根据时间排序查询
        return Hib.query(session ->{
            return (List<M3U8TelePlayUrl>)session.createQuery("from M3U8TelePlayUrl where movieId=:movieId")
                .setParameter("movieId",movieId)
                .list();
            });
    }
    /**
     *查询影视url
     * @return
     */
    public static List<TelePlayUrl2> geTeleplayUrl2(String movieId){
        //根据时间排序查询
        return Hib.query(session ->{
            return (List<TelePlayUrl2>) session.createQuery("from TelePlayUrl2 where movieId=:movieId")
                .setParameter("movieId",movieId)
                .list();
            });
    }
    /**
     * 保存爬取的新闻
     * @param titles
     * @param imgs
     * @param urls
     * @param type
     */
    public static void saveNews(List<String> titles,List<String> imgs,List<String> urls,String type){
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
                    testReptile.setType(type);
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

    }

    public static void main(String[] args){

       System.out.println("----->"+ geTeleplayUrl("1d55d7d1-3395-4a45-ad25-8fdbf744f2d1").size());
    }
}
