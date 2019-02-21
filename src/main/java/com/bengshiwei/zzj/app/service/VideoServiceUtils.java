package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoListModel;
import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.utils.Hib;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 新闻
 * Created by JamesZhang on 2018/5/7.
 */
public class VideoServiceUtils {

    //查询消息
    @SuppressWarnings("unchecked")
    public static List<VideoListModel> getVideoList(String mType, int page, int limit){

        int beginPage =  page*limit;

        List<MovieDetailsModel> movieDetailsModels =Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return  session.createQuery("from MovieDetailsModel where type=:mType  order by updateTime desc")
                    .setParameter("mType",mType)
                    .setMaxResults(limit)
                    .setFirstResult(beginPage)
                    .list();

        });

        return  movieDetailsModels.stream().map(new Function<MovieDetailsModel, VideoListModel>() {
            @Override
            public VideoListModel apply(MovieDetailsModel movieDetailsModel) {
                VideoListModel videoListModel = new VideoListModel();
                videoListModel.setId(movieDetailsModel.getId());
                videoListModel.setImg(movieDetailsModel.getImg());
                videoListModel.setTitle(movieDetailsModel.getTitle());
                videoListModel.setMovieType(movieDetailsModel.getMovieType());
                videoListModel.setType(movieDetailsModel.getType());
                videoListModel.setUpdateTime(movieDetailsModel.getUpdateTime());
                return videoListModel;
            }
        }).collect(Collectors.toList());
    }



    /**
     * 根据id查询电影数据
     * @param movieId
     * @return
     */
    public static MovieDetailsModel findVideoById(String movieId){

        return Hib.query(session -> session.get(MovieDetailsModel.class,movieId));
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
