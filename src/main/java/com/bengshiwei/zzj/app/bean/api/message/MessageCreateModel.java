package com.bengshiwei.zzj.app.bean.api.message;

import com.bengshiwei.zzj.app.bean.db.ChatMessage;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * API请求的Model格式
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class MessageCreateModel {
    // ID从客户端生产，一个UUID
    @Expose
    private String id;
    @Expose
    private String content;
    @Expose
    private String attach;

    // 消息类型
    @Expose
    private int type = ChatMessage.TYPE_STR;

    // 接收者 可为空
    @Expose
    private String receiverId;

    // 接收者类型，群，人
    @Expose
    private int receiverType = ChatMessage.RECEIVER_TYPE_NONE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public static boolean check(MessageCreateModel model) {
        return model != null
                && !(Strings.isNullOrEmpty(model.id)
                || Strings.isNullOrEmpty(model.content)
                || Strings.isNullOrEmpty(model.receiverId))

                && (model.receiverType == ChatMessage.RECEIVER_TYPE_NONE
                || model.receiverType == ChatMessage.RECEIVER_TYPE_GROUP)

                && (model.type == ChatMessage.TYPE_STR
                || model.type == ChatMessage.TYPE_AUDIO
                || model.type == ChatMessage.TYPE_FILE
                || model.type == ChatMessage.TYPE_PIC);
    }
}
