package com.bengshiwei.zzj.app.bean.api.video;

import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.TelePlayUrl;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JamesZhang on 2018/4/22.
 */

public class VideoListModel {




    @Expose
    private String id;
    //unique = true  不允许重复

    @Expose
    private String title;

    @Expose
    private String img;


    @Expose
    private String type;//新闻类型

    //更新时间

    @Expose
    private String updateTime;

    @Expose
    private String movieType;//电影的类型0是最新，1是国产，2美剧3英剧4韩剧5日剧


    public VideoListModel() {
    }

    public VideoListModel(MovieDetailsModel movieDetailsModel) {
        this.id = movieDetailsModel.getId();
        this.title = movieDetailsModel.getTitle();
        this.img = movieDetailsModel.getImg();
        this.type = movieDetailsModel.getType();
        this.updateTime = movieDetailsModel.getUpdateTime();
        this.movieType = movieDetailsModel.getMovieType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }
}
