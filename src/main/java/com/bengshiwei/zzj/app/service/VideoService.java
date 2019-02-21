package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.api.NewsReptileModel;
import com.bengshiwei.zzj.app.bean.api.video.VideoListModel;
import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.MovieDetailsModel;
import com.bengshiwei.zzj.app.bean.db.NewsBrowse;
import com.bengshiwei.zzj.app.bean.db.NewsReptile;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.factory.UserFactory;
import com.bengshiwei.zzj.app.utils.Hib;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Created by JamesZhang on 2018/5/7.
 */
@Path("/video")
public class VideoService {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    @GET
    @Path("/login")
    public String get() {
        return "you get the login";
    }

    @POST
    @Path("/videoList")
    //指定请求与返回的相应体为Json
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces()
    public ResponseModel<List<VideoListModel>> getNewsList(@FormParam("type") String type, @FormParam("page") int page,@FormParam("limit") int limit) {

        List<VideoListModel> videoList = VideoServiceUtils.getVideoList(type, page,limit);
        if (videoList != null) {
            if (videoList.size() > 0) {
                return ResponseModel.buildOk(videoList);
            } else {
                return ResponseModel.buildMessage(ResponseModel.DATA_EMPTY, "数据为空");
            }
        } else {
            return ResponseModel.buildMessage(ResponseModel.DATA_EMPTY, "查询失败");
        }

    }
    @POST
    @Path("/videoDetails")
    public ResponseModel<MovieDetailsModel> getVideoDetails(@FormParam("movieId")String movieId){

        MovieDetailsModel movieDetailsModel = VideoServiceUtils.findVideoById(movieId);
//        movieDetailsModel.setTelePlayUrls(movieDetailsModel.getTelePlayUrls());
        if(movieDetailsModel!=null){
            return ResponseModel.buildOk(movieDetailsModel);
        }else {
            return ResponseModel.buildQueryError();
        }

    }
}
