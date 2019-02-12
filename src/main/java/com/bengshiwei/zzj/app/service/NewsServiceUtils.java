package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.utils.Hib;
import org.hibernate.Session;

import java.util.List;

/**
 * 新闻
 * Created by JamesZhang on 2018/5/7.
 */
public class NewsServiceUtils {

    //查询消息
    @SuppressWarnings("unchecked")
    public static List<NewsReptileModel> getNewsList(String mType, int page){

        int beginPage =  page*10;

        return Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return  session.createQuery("from NewsReptile where type=:mType  order by createAt desc")
                    .setParameter("mType",mType)
                    .setMaxResults(10)
                    .setFirstResult(beginPage)
                    .list();

        });
    }

    /**
     * 根据id查询新闻数据
     * @param newsId
     * @return
     */
    public static NewsReptile findNewsById(String newsId){

        return Hib.query(session -> session.get(NewsReptile.class,newsId));
    }

    /**
     * 查询当前新闻是否保存过
     * @param newsId
     * @return
     */
    public static NewsBrowse findNewsBrowserById(String userId, String newsId){
        return Hib.query(session -> {
            return (NewsBrowse) session.createQuery("from NewsBrowse where userId=:userid and newsId=:newsid")
                    .setParameter("userid",userId)
                    .setParameter("newsid",newsId)
                    .uniqueResult();
        });
    }

    /**
     *查询当前用户的浏览记录
     * @return
     */
    public static List<NewsBrowse> getNewsBrowseList(String userid){
        //根据时间排序查询
        return Hib.query(session -> session.createQuery("from NewsBrowse where userId=:userid order by updateAt desc")
                .setParameter("userid",userid)
                .list());
    }
}
