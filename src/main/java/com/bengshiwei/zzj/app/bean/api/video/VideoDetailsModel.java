package com.bengshiwei.zzj.app.bean.api.video;

import com.bengshiwei.zzj.app.bean.db.TelePlayUrl;
import com.bengshiwei.zzj.app.bean.db.TelePlayUrl2;
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


public class VideoDetailsModel {


    @Expose
    private String id;
    //unique = true  不允许重复


    @Expose
    private String title;

    @Expose
    private String img;

    @Expose
    private String url;


    @Expose()
    private String playUrl;

    @Expose()
    private String playUrl2;

    @Expose
    private Set<TelePlayUrl> playUrls = new HashSet<>();

    @Expose
    private Set<TelePlayUrl2> playUrls2 = new HashSet<>();


    @Expose
    private String type;//新闻类型

    @Expose
    private String source;//新闻来源

    @Expose
    private String updateTime;

    @Expose
    private String definition;

    @Expose
    private String director;//导演

    @Expose
    private String actor;//演员

    @Expose
    private String region;//区域

    @Expose
    private String movieDesc;//描述

    @Expose
    private String movieType;//电影的类型0是最新，1是国产，2美剧3英剧4韩剧5日剧

    @Expose
    private LocalDateTime createAt = LocalDateTime.now();

    @Expose
    private LocalDateTime updateAt = LocalDateTime.now();



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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }



    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public Set<TelePlayUrl> getTelePlayUrls() {
        return playUrls;
    }

    public void setTelePlayUrls(Set<TelePlayUrl> telePlayUrls) {
        this.playUrls = telePlayUrls;
    }

    public String getPlayUrl2() {
        return playUrl2;
    }

    public void setPlayUrl2(String playUrl2) {
        this.playUrl2 = playUrl2;
    }

    public Set<TelePlayUrl2> getPlayUrls2() {
        return playUrls2;
    }

    public void setPlayUrls2(Set<TelePlayUrl2> playUrls2) {
        this.playUrls2 = playUrls2;
    }

}
