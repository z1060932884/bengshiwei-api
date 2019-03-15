package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.base.ResponseModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * 版本升级接口
 */
@Path("/updateVersion")
public class UpdateVersionService {

    @GET
    @Path("/login")
    public String get(){
        return "you get the login";
    }


    /**
     * 获取最新版本信息
     * @return
     */
    @GET
    @Path("/getVersion")
    public ResponseModel getVersion(){

        return UpdateVersionUtils.getVersion();
    }
}
