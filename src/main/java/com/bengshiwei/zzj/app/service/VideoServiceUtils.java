package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoDetailsModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoListModel;
import com.bengshiwei.zzj.app.bean.db.*;
import com.bengshiwei.zzj.app.utils.Hib;
import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 新闻
 * Created by JamesZhang on 2018/5/7.
 */
public class VideoServiceUtils {

    //查询消息
    @SuppressWarnings("unchecked")
    public static List<VideoListModel> getVideoList(String mType, int page, int limit) {

        int beginPage = page * limit;

        List<MovieDetailsModel> movieDetailsModels = Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return session.createQuery("from MovieDetailsModel where type=:mType  order by updateTime desc")
                    .setParameter("mType", mType)
                    .setMaxResults(limit)
                    .setFirstResult(beginPage)
                    .list();

        });

        return movieDetailsModels.stream().map(new Function<MovieDetailsModel, VideoListModel>() {
            @Override
            public VideoListModel apply(MovieDetailsModel movieDetailsModel) {

                return new VideoListModel(movieDetailsModel);
            }
        }).collect(Collectors.toList());
    }

    //查询消息
    @SuppressWarnings("unchecked")
    public static List<VideoListModel> getM3u8VideoList(String mType, int page, int limit) {

        int beginPage = page * limit;

        List<M3U8MovieDetailsModel> movieDetailsModels = Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return session.createQuery("from M3U8MovieDetailsModel where type=:mType  order by updateTime desc")
                    .setParameter("mType", mType)
                    .setMaxResults(limit)
                    .setFirstResult(beginPage)
                    .list();

        });

        return movieDetailsModels.stream().map(new Function<M3U8MovieDetailsModel, VideoListModel>() {
            @Override
            public VideoListModel apply(M3U8MovieDetailsModel movieDetailsModel) {
                VideoListModel videoListModel = new VideoListModel();
                videoListModel.setUpdateTime(movieDetailsModel.getUpdateTime());
                videoListModel.setType(movieDetailsModel.getType());
                videoListModel.setMovieType(movieDetailsModel.getMovieType());
                videoListModel.setTitle(movieDetailsModel.getTitle());
                videoListModel.setImg(movieDetailsModel.getImg());
                videoListModel.setId(movieDetailsModel.getId());
                return videoListModel;
            }
        }).collect(Collectors.toList());
    }


    public static List<TelePlayUrl> getPlayUrlList(String movieId, int page, int limit) {

        int beginPage = page * limit;

        List<TelePlayUrl> telePlayUrls = Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return session.createQuery("from TelePlayUrl where movieId=:movieId")
                    .setParameter("movieId", movieId)
                    .list();

        });

        return telePlayUrls;
    }


    /**
     * 根据id查询电影数据
     *
     * @param movieId
     * @return
     */
    public static VideoDetailsModel findVideoById(String movieId) {
        VideoDetailsModel videoDetailsModel = null;
        try{
            MovieDetailsModel movieDetailsModel = Hib.query(session -> session.get(MovieDetailsModel.class, movieId));
            videoDetailsModel = new VideoDetailsModel();
            videoDetailsModel.setId(movieDetailsModel.getId());
            videoDetailsModel.setActor(movieDetailsModel.getActor());
            videoDetailsModel.setCreateAt(movieDetailsModel.getCreateAt());
            videoDetailsModel.setDefinition(movieDetailsModel.getDefinition());
            videoDetailsModel.setDirector(movieDetailsModel.getDirector());
            videoDetailsModel.setImg(movieDetailsModel.getImg());
            videoDetailsModel.setMovieDesc(movieDetailsModel.getMovieDesc());
            videoDetailsModel.setPlayUrl(movieDetailsModel.getPlayUrl());
            videoDetailsModel.setPlayUrl2(movieDetailsModel.getPlayUrl());
            videoDetailsModel.setPlayUrl(movieDetailsModel.getPlayUrl2());
            videoDetailsModel.setTitle(movieDetailsModel.getTitle());
            videoDetailsModel.setTelePlayUrls(movieDetailsModel.getTelePlayUrls());
            videoDetailsModel.setUpdateTime(movieDetailsModel.getUpdateTime());
            videoDetailsModel.setType(movieDetailsModel.getType());
            videoDetailsModel.setMovieType(movieDetailsModel.getMovieType());
            videoDetailsModel.setRegion(movieDetailsModel.getRegion());
        }catch (Exception e){
            return null;

        }
        return videoDetailsModel;

    }

    /**
     * 根据id查询电影数据
     *
     * @param movieId
     * @return
     */
    public static VideoDetailsModel findM3u8VideoById(String movieId) {
        VideoDetailsModel videoDetailsModel = null;
        try {
            M3U8MovieDetailsModel movieDetailsModel = Hib.query(session -> session.get(M3U8MovieDetailsModel.class, movieId));
            videoDetailsModel = new VideoDetailsModel();
            videoDetailsModel.setId(movieDetailsModel.getId());
            videoDetailsModel.setActor(movieDetailsModel.getActor());
            videoDetailsModel.setCreateAt(movieDetailsModel.getCreateAt());
            videoDetailsModel.setDefinition(movieDetailsModel.getDefinition());
            videoDetailsModel.setDirector(movieDetailsModel.getDirector());
            videoDetailsModel.setImg(movieDetailsModel.getImg());
            videoDetailsModel.setMovieDesc(movieDetailsModel.getMovieDesc());
            videoDetailsModel.setPlayUrl(movieDetailsModel.getPlayUrl());
            videoDetailsModel.setPlayUrl2(movieDetailsModel.getPlayUrl());
            videoDetailsModel.setPlayUrl(movieDetailsModel.getPlayUrl2());
            videoDetailsModel.setTitle(movieDetailsModel.getTitle());
            Iterator iter =  movieDetailsModel.getTelePlayUrls().iterator();
            Set<TelePlayUrl> telePlayUrls = new HashSet<>();
            while (iter.hasNext()){
                M3U8TelePlayUrl m3U8TelePlayUrl = (M3U8TelePlayUrl) iter.next();
                TelePlayUrl telePlayUrl = new TelePlayUrl();
                telePlayUrl.setId(m3U8TelePlayUrl.getId());
                telePlayUrl.setPlayUrl(m3U8TelePlayUrl.getPlayUrl());
                telePlayUrl.setMovieId(m3U8TelePlayUrl.getMovieId());
                telePlayUrls.add(telePlayUrl);
            }
            videoDetailsModel.setTelePlayUrls(telePlayUrls);
            videoDetailsModel.setUpdateTime(movieDetailsModel.getUpdateTime());
            videoDetailsModel.setType(movieDetailsModel.getType());
            videoDetailsModel.setMovieType(movieDetailsModel.getMovieType());
            videoDetailsModel.setRegion(movieDetailsModel.getRegion());
        }catch (Exception e){

            return null;
        }
        return videoDetailsModel;
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

    public static void main(String[] args) {

        System.out.println(System.currentTimeMillis());
//        System.out.println(VideoServiceUtils.getPlayUrlList(search("我的亲爹和后爸").get(0).getId(),0,0).size());

//        MovieDetailsModel movieDetailsModel = VideoServiceUtils.findVideoById(search("我的亲爹和后爸").get(0).getId());


//        if (movieDetailsModel != null) {
//            System.out.println("-------" + movieDetailsModel.getTelePlayUrls().size());
//
//        } else {
//            System.out.println("-------null");
//        }
        System.out.println(System.currentTimeMillis());

    }
}
