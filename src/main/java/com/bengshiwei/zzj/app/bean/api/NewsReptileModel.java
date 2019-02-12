package com.bengshiwei.zzj.app.bean.api;

import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by JamesZhang on 2018/4/22.
 */
public class NewsReptileModel implements Serializable{
    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String img;
    @Expose
    private String url;
    @Expose
    private String type;
    @Expose
    private String source;

    @Expose
    private LocalDateTime createAt;// 创建时间
    @Expose
    private LocalDateTime updateAt;


    public NewsReptileModel(NewsBrowse newsBrowse){
        this.id = newsBrowse.getNewsId();
        this.title = newsBrowse.getNewsTitle();
        this.img = newsBrowse.getNewsImage();
        this.type = newsBrowse.getNewsType();
        this.updateAt = newsBrowse.getUpdateAt();
        this.url = newsBrowse.getNewsUrl();
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
}
