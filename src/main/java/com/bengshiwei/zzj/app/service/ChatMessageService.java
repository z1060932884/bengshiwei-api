package com.bengshiwei.zzj.app.service;


import com.bengshiwei.zzj.app.bean.api.ChatMessageModel;
import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.ChatMessage;
import com.bengshiwei.zzj.app.factory.UserFactory;
import com.bengshiwei.zzj.app.push.Demo;
import com.bengshiwei.zzj.app.utils.Constants;
import com.bengshiwei.zzj.app.utils.Hib;
import org.hibernate.Session;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by JamesZhang on 2018/5/10.
 */
@Path("/chat")
public class ChatMessageService {

    @POST
    @Path("/sendMsg")
    //指定请求与返回的相应体为Json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel sendMsg(ChatMessageModel chatMessageModel){
        ChatMessage chatMessage = new ChatMessage();
        if(chatMessageModel!=null){
            chatMessage.setId(chatMessageModel.getId());
            chatMessage.setContent(chatMessageModel.getContent());
            chatMessage.setType(chatMessageModel.getType());
            chatMessage.setReceiverId(chatMessageModel.getReceiverId());
            chatMessage.setSenderId(chatMessageModel.getSenderId());
            chatMessage.setSender(UserFactory.findById(chatMessageModel.getSenderId()));
            chatMessage.setReceiver(UserFactory.findById(chatMessageModel.getReceiverId()));
            Session session = Hib.session();
            //开启一个事物
            session.beginTransaction();
            try{
                //保存操作
                session.save(chatMessage);
                //提交事物
                session.getTransaction().commit();

                Demo demo = new Demo(Constants.UMENG_APP_KEY,Constants.UMNENG_APP_MASTER_SECRET);
                demo.sendAndroidCustomizedcast(chatMessage);

            }catch (Exception e){
                //如果失败情况下事件回滚
                session.getTransaction().rollback();
                System.out.println("------sendMsg--->"+e.getMessage());
                return ResponseModel.buildSendMessageFails(chatMessage);
            }

        }else {
        /*    responseModel.setData(null);
            responseModel.setCode(202);
            responseModel.setMsg("消息发送失败,发送数据为空");*/
            return ResponseModel.buildServiceError();
        }

        return ResponseModel.buildOk(chatMessage);

    }
}
