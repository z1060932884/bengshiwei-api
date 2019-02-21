package com.bengshiwei.zzj.app.bean.db;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JamesZhang on 2018/4/22.
 */

@Entity
@Table(name = "bsw_movie_detail")
public class MovieDetailsModel {

    @Id // 这是一个主键
    @PrimaryKeyJoinColumn
    //主键生成存储的类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false,nullable = false)
    @Expose
    private String id;
    //unique = true  不允许重复

    @Column()
    @Expose
    private String title;
    @Column()
    @Expose
    private String img;
    @Column()
    @Expose
    private String url;

    @Column(columnDefinition = "TEXT")
    @Expose()
    private String playUrl;

    // 我关注的人的列表方法
    // 对应的数据库表字段为Teleplayurl movieId
    @JoinColumn(name = "movieId")
    // 定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1对多，一个用户可以有很多关注人，每一次关注都是一个记录
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Set<TelePlayUrl> playUrls = new HashSet<>();

    @Column()
    @Expose
    private String type;//新闻类型
    @Column()
    @Expose
    private String source;//新闻来源
    //更新时间
    @Column()
    @Expose
    private String updateTime;
    //清晰度
    @Column()
    @Expose
    private String definition;
    @Column()
    @Expose
    private String director;//导演
    @Column()
    @Expose
    private String actor;//演员
    @Column()
    @Expose
    private String region;//区域
    @Column(columnDefinition = "TEXT")
    @Expose
    private String movieDesc;//描述
    @Column()
    @Expose
    private String movieType;//电影的类型0是最新，1是国产，2美剧3英剧4韩剧5日剧
//    // 定义为创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义为更新时间戳，在创建时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
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
}
