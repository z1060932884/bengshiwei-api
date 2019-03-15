package com.bengshiwei.zzj.app.service;

import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.UpdateVersion;
import com.bengshiwei.zzj.app.utils.Hib;

import java.util.List;

/**
 * 版本更新的工具类
 */
public class UpdateVersionUtils {

    public static ResponseModel getVersion(){

        List<UpdateVersion> updateVersion =   Hib.query(session -> {
          return (List<UpdateVersion>) session.createQuery("from UpdateVersion order by updateAt")
                    .setMaxResults(1)
                   .list();
        });

        if(updateVersion!=null&&updateVersion.size()>0){
            ResponseModel.buildOk(updateVersion.get(0));
        }
        return ResponseModel.buildQueryError();
    }
}
