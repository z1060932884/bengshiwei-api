package com.bengshiwei.zzj.app.bean.db;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 新闻浏览记录数据库
 *
 */
@Entity
@Table(name = "BWS_NEWS_BROWSE")
public class NewsBrowse {
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
    @Column(updatable = false,nullable = false)
    @Expose
    private String userId;
    @Column(updatable = false,nullable = false)
    @Expose
    private String newsId;
    @Column(updatable = false,nullable = false)
    @Expose
    private String newsTitle;
    @Column(updatable = false,nullable = false)
    @Expose
    private String newsImage;
    @Column(updatable = false,nullable = false)
    @Expose
    private String newsType;
    @Column(updatable = false,nullable = false)
    @Expose
    private String newsUrl;
    @Column(nullable = false)
    @Expose
    private int isIntegral = 0;//是否返回积分 0是未返回，1是返回
    // 定义为创建时间戳，在创建时就已经写入
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
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

    public int isIntegral() {
        return isIntegral;
    }

    public void  setIntegral(int integral) {
        this.isIntegral = integral;
    }
}
