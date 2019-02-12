package com.bengshiwei.zzj.app.bean.api;


import com.bengshiwei.zzj.app.bean.db.ChatMessage;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by zzj on 2018/5/10.
 */

public class ChatMessageModel implements Serializable {

    @Expose
    private String id;//消息id
    @Expose
    private String senderId;//发送者id
    @Expose
    private String receiverId;//接收者id
    @Expose
    private int type;//消息类型
    @Expose
    private String content;//消息
    @Expose
    private String sender;//发送者
    @Expose
    private String receiver;//接收者

    // 定义为创建时间戳，在创建时就已经写入
    @Expose
    private LocalDateTime createAt = LocalDateTime.now();

    // 定义为更新时间戳，在创建时就已经写入
    @Expose
    private LocalDateTime updateAt = LocalDateTime.now();


    public ChatMessageModel(ChatMessage chatMessage){
        this.id = chatMessage.getId();
        this.senderId = chatMessage.getSenderId();
        this.receiverId = chatMessage.getReceiverId();
        this.type = chatMessage.getType();
        this.content = chatMessage.getContent();
        this.sender = chatMessage.getSender().getName();
        this.receiver = chatMessage.getReceiver().getName();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int itemType) {
        this.type = itemType;
    }

    public int getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
