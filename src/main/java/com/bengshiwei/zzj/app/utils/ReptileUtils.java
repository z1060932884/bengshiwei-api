package com.bengshiwei.zzj.app.utils;


import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
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
}
