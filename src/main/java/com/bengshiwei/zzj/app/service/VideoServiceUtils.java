package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoListModel;
import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.utils.Hib;
import com.google.common.base.Strings;

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

        List<MovieDetailsModel> movieDetailsModels = Hib.query(session -> {
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

                return new VideoListModel(movieDetailsModel);
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
     * 搜索视频的实现
     *
     * @param name 查询的name，允许为空
     * @return 查询到的用户集合，如果name为空，则返回最近的用户
     */
    @SuppressWarnings("unchecked")
    public static List<VideoListModel> search(String name) {
        if (Strings.isNullOrEmpty(name))
            name = ""; // 保证不能为null的情况，减少后面的一下判断和额外的错误
        final String searchName = "%" + name + "%"; // 模糊匹配

        List<MovieDetailsModel> movieDetailsModels = Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return session.createQuery("from MovieDetailsModel where lower(title) like :name ")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 至多20条
                    .list();
        });
        return movieDetailsModels.stream().map(movieDetailsModel -> new VideoListModel(movieDetailsModel)).collect(Collectors.toList());

    }
}
