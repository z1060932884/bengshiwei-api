package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.card.UserCard;
import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.factory.UserFactory;
import com.bengshiwei.zzj.app.utils.Hib;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Created by JamesZhang on 2018/5/7.
 */
@Path("/news")
public class NewsService {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    @GET
    @Path("/login")
    public String get() {
        return "you get the login";
    }

    @POST
    @Path("/newsList")
    //指定请求与返回的相应体为Json
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces()
    public ResponseModel<List<NewsReptileModel>> getNewsList(@FormParam("type") String type, @FormParam("page") int page) {

        List<NewsReptileModel> newsReptiles = NewsServiceUtils.getNewsList(type, page);
        if (newsReptiles != null) {
            if (newsReptiles.size() > 0) {
                return ResponseModel.buildOk(newsReptiles);
            } else {
                return ResponseModel.buildMessage(ResponseModel.DATA_EMPTY, "数据为空");
            }
        } else {
            return ResponseModel.buildMessage(ResponseModel.DATA_EMPTY, "查询失败");
        }

    }

    /**
     * 上传用户浏览记录
     *
     * @return
     */
    @POST()
    @Path("/uploadNewsBrowse")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces()
    public ResponseModel uploadNewsBrowse(@FormParam("userId") String userId, @FormParam("newsId") String newsId) {

//        logger.info("999999999999999----->"+newsReptile.getTitle());
        //判断当前新闻是否查看保存过
        NewsBrowse newsBrowse = NewsServiceUtils.findNewsBrowserById(userId, newsId);
        if (newsBrowse != null) {
            newsBrowse.setUpdateAt(LocalDateTime.now());
        } else {
            //查询新闻数据
            NewsReptile newsReptile = NewsServiceUtils.findNewsById(newsId);
            newsBrowse = new NewsBrowse();
            newsBrowse.setUserId(userId);
            newsBrowse.setNewsId(newsReptile.getId());
            newsBrowse.setNewsTitle(newsReptile.getTitle());
            newsBrowse.setNewsImage(newsReptile.getImg());
            newsBrowse.setNewsUrl(newsReptile.getUrl());
            newsBrowse.setNewsType(newsReptile.getType());
        }
        Session session = Hib.session();
        //开启事务
        session.beginTransaction();

        try {
            session.saveOrUpdate(newsBrowse);
            session.getTransaction().commit();

        } catch (Exception e) {
            //异常事务回滚
            session.getTransaction().rollback();
            return ResponseModel.buildFails(newsBrowse);
        }
        return ResponseModel.buildOk(newsBrowse);
    }

    /**
     * 浏览新闻送积分 如果浏览的是已经浏览过的不送，只有第一次浏览的新闻送积分
     *
     * @param userId
     * @param newsId
     * @return
     */
    @PUT
    @Path("/newsAwardPoints")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces()
    public static ResponseModel browseNewsAwardPoints(@FormParam("userId") String userId, @FormParam("newsId") String newsId) {
        //1、查询当前新闻是否被浏览
        NewsBrowse newsBrowse = NewsServiceUtils.findNewsBrowserById(userId, newsId);
        if (newsBrowse != null) {
//            新闻被浏览不加积分
            if (newsBrowse.isIntegral() == 1) {
                return ResponseModel.buildMessage(ResponseModel.FAILS, "该新闻积分已添加，请浏览未读新闻");
            }
            //设置已添加积分
            newsBrowse.setIntegral(1);
            //当前新闻未被浏览过，
            //1、查找当前用户
            User user = UserFactory.findById(userId);
            //当前积分加 1
            user.setIntegral(Integer.valueOf(user.getIntegral()) + 1 + "");
            Session session1 = Hib.session();
            session1.beginTransaction();
            try {
                session1.saveOrUpdate(user);
                session1.saveOrUpdate(newsBrowse);
                session1.getTransaction().commit();
            } catch (Exception e) {
                session1.getTransaction().rollback();
                //返回失败信息
                return ResponseModel.buildFails(user);
            }
            return ResponseModel.buildOk(user);
        }
        return ResponseModel.buildMessage(ResponseModel.FAILS, "新闻记录为空");
    }

    /**
     * 获取用户浏览记录接口
     * @param userId
     * @return
     */
    @POST
    @Path("/getNewsBrowseList")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public static ResponseModel getNewsBrowseList(@FormParam("userId") String userId){
        List<NewsBrowse> newsBrowseList = NewsServiceUtils.getNewsBrowseList(userId);
        if(newsBrowseList!=null&&newsBrowseList.size()>0){
            // 把NewsBrowse->NewsReptileModel
            List<NewsReptileModel> newsReptileModels = newsBrowseList.stream()
                    .map(user -> {
                        return new NewsReptileModel(user);
                    }).collect(Collectors.toList());
            // 返回
            return ResponseModel.buildOk(newsReptileModels);
        }
        return ResponseModel.buildMessage(ResponseModel.FAILS,"数据为空");
    }

}
