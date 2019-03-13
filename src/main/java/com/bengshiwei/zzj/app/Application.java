package com.bengshiwei.zzj.app;

import com.bengshiwei.zzj.app.provider.GsonProvider;
import com.bengshiwei.zzj.app.reptile.ShanDongNewsReptile;
import com.bengshiwei.zzj.app.service.AccountService;
import com.bengshiwei.zzj.app.utils.ZuiDaVideoReptileUtils;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

public class Application extends ResourceConfig {


    public Application(){
        //注册逻辑转换器
        packages(AccountService.class.getPackage().getName());
        //注册 MultiPart
        register(MultiPartFeature.class);
        //注册json转换器
        register(GsonProvider.class);

        //注册日志打印输出
        register(Logger.class);


//        ZuiDaVideoReptileUtils.startReptile();
//
//        ShanDongNewsReptile.startReptile();

    }

}
