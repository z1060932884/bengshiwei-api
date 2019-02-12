package com.bengshiwei.zzj.app.service;


import com.bengshiwei.zzj.app.bean.api.AccountInfoModel;
import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.reptile.SeleniumDownloader;
import com.bengshiwei.zzj.app.reptile.SinaYuLeNewsReptile;
import com.bengshiwei.zzj.app.utils.Hib;
import org.hibernate.Session;
import us.codecraft.webmagic.Spider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JamesZhang on 2018/5/4.
 */
@Path("/account")
public class AccountService {
    @GET
    @Path("/login")
    public String get(){
       /* Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Spider.create(new SinaYuLeNewsReptile())
                        .addUrl("http://ent.sina.com.cn/")
                        .setDownloader(new SeleniumDownloader("C:\\Users\\JamesZhang\\Desktop\\reptile\\chromedriver.exe")).thread(5).run();
            }
        }, 1000);*/
        return "you get the login";
    }

    @POST
    @Path("/register")
    //指定请求与返回的相应体为Json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel register(AccountInfoModel model){


        User findUser = findByName(model.getName());

        //进行数据库操作
        //首先创建一个回话

        if(findUser!=null&&findUser.getName().equals(model.getName())){

            return ResponseModel.buildHaveAccountError();
        }


        User user = new User();
        user.setName(model.getName());
        user.setPassword(model.getPassword());
        user.setAddress(model.getAddress());
        Session session = Hib.session();
        //开启一个事物
        session.beginTransaction();
        try{
            //保存操作
            session.save(user);
            //提交事物
            session.getTransaction().commit();

        }catch (Exception e){
            //如果失败情况下事件回滚
            session.getTransaction().rollback();

            return ResponseModel.buildRegisterError();
        }



        return ResponseModel.buildOk(user);

//        User user = new User();
//        user.setName("zzj");
//        user.setSex(1);
//
//        return user;
    }

    @POST
    @Path("/login")
    //指定请求与返回的相应体为Json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel login(AccountInfoModel model){
        ResponseModel<User> responseModel = new ResponseModel<>();
        if(model!=null){
            //通过传入的name查找数据库中对应的user
            User user = findByName(model.getName());
            if(user!=null){
                if(user.getPassword().equals(model.getPassword())){
                    responseModel.setMessage("登录成功");
                    responseModel.setCode(0);
                    responseModel.setResult(user);
                }else {
                    responseModel.setMessage("用户名或密码错误");
                    responseModel.setCode(1);
                    responseModel.setResult(null);
                }
            }else {
                responseModel.setMessage("该用户未注册");
                responseModel.setCode(3);
                responseModel.setResult(null);
            }
        }
        return responseModel;
    }



    // 通过Phone找到User
    @SuppressWarnings("unchecked")
    public static User findByName(String name) {
        return Hib.query(session -> {
            return (User) session
                    .createQuery("from User where name=:name")
                    .setParameter("name", name)
                    .uniqueResult();
        });
    }
}
