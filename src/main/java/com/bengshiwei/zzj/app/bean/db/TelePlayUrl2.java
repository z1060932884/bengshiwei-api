package com.bengshiwei.zzj.app.bean.db;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bsw_movie_teleplay_url2")
public class TelePlayUrl2 {

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

    @ManyToOne(optional = false)
    // 定义关联的表字段名为originId，对应的是User.id
    // 定义的是数据库中的存储字段
    @JoinColumn(name = "movieId")
    private MovieDetailsModel movieDetailsModel;
    //unique = true  不允许重复
    @Column(updatable = false,nullable = false,insertable=false)
    private String movieId;

    @Column(columnDefinition = "TEXT")
    @Expose()
    private String playUrl;


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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public MovieDetailsModel getMovieDetailsModel() {
        return movieDetailsModel;
    }

    public void setMovieDetailsModel(MovieDetailsModel movieDetailsModel) {
        this.movieDetailsModel = movieDetailsModel;
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
