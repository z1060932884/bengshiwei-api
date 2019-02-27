package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoListModel;
import com.bengshiwei.zzj.app.bean.db.*;
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


    public static List<TelePlayUrl> getPlayUrlList(String movieId, int page, int limit){

        int beginPage =  page*limit;

        List<TelePlayUrl> telePlayUrls = Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return  session.createQuery("from TelePlayUrl where movieId=:movieId")
                    .setParameter("movieId",movieId)
                    .list();

        });

        return  telePlayUrls;
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

    public static void main(String[] args){

//        System.out.println(search("海贼王").get(0).getId());
        System.out.println(VideoServiceUtils.getPlayUrlList("0703d45b-e79e-46d5-aa92-133ad6456b75",0,0).size());

//       MovieDetailsModel movieDetailsModel =  VideoServiceUtils.findVideoById("0703d45b-e79e-46d5-aa92-133ad6456b75");
//
//
//       if(movieDetailsModel!=null){
//           System.out.println( "-------"+movieDetailsModel.getTelePlayUrls().size());
//
//       }else {
//           System.out.println( "-------null");
//       }

    }
}
