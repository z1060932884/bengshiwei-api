package com.bengshiwei.zzj.app.service;


import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.bean.card.UserCard;
import com.bengshiwei.zzj.app.factory.UserFactory;
import com.bengshiwei.zzj.app.utils.Hib;
import org.hibernate.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by JamesZhang on 2018/5/16.
 */
@Path("/user")
public class UserService {

    @GET
    @Path("/login")
    public String get(){
        return "you get the login";
    }


    // 搜索人的接口实现
    // 为了简化分页：只返回20条数据
    @GET // 搜索人，不涉及数据更改，只是查询，则为GET
    // http://127.0.0.1/api/user/search/
    @Path("/search") // 名字为任意字符，可以为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> search(@QueryParam("userId") String userId,@DefaultValue("") @QueryParam("name") String name) {
        User self =  UserFactory.findById(userId);

        // 先查询数据
        List<User> searchUsers = UserFactory.search(name);
        // 把查询的人封装为UserCard
        // 判断这些人是否有我已经关注的人，
        // 如果有，则返回的关注状态中应该已经设置好状态

        // 拿出我的联系人
        final List<User> contacts = UserFactory.contacts(self);

        // 把User->UserCard
        List<UserCard> userCards = searchUsers.stream()
                .map(user -> {
                    // 判断这个人是否是我自己，或者是我的联系人中的人
                    boolean isFollow = user.getId().equalsIgnoreCase(self.getId())
                            // 进行联系人的任意匹配，匹配其中的Id字段
                            || contacts.stream().anyMatch(
                            contactUser -> contactUser.getId()
                                    .equalsIgnoreCase(user.getId())
                    );

                    return new UserCard(user, isFollow);
                }).collect(Collectors.toList());
        // 返回
        return ResponseModel.buildOk(userCards);
    }

    // 拉取联系人
    @GET
    @Path("/contact/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel contact(@PathParam("userId") String userId) {
        //获取当前user
        User self =  UserFactory.findById(userId);

        //获取当前用户的联系人
        // 获取我关注的人
        List<User> userFollowList =   UserFactory.contacts(self);
        // 转换为UserCard
        List<UserCard> userCards = userFollowList.stream()
                // map操作，相当于转置操作，User->UserCard
                .map(user -> new UserCard(user, true))
                .collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }

    // 关注人，
    // 简化：关注人的操作其实是双方同时关注
    @POST // 修改类使用Put
    @Path("/follow")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel follow(@FormParam("originId") String originId, @FormParam("followId") String followId) {
        //自己
        User originUser =  UserFactory.findById(originId);
        // /查找到添加的人
        User followUser = UserFactory.findById(followId);
        if (followUser == null) {
            // 未找到人
            return ResponseModel.buildNotFoundUserError(null);
        }

        // 备注默认没有，后面可以扩展
        followUser =  UserFactory.follow(originUser, followUser, null);
        if (followUser == null) {
            // 关注失败，返回服务器异常
            return ResponseModel.buildServiceError();
        }
        return ResponseModel.buildOk(new UserCard(followUser, true));
    }

    /**
     * 修改用户头像
     * @param id
     * @return
     */
    @POST
    @Path("/modificationUserPic")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public  ResponseModel modificationUserPic(@FormParam("userId") String id,@FormParam("picPath") String picPath ){

        return UserFactory.modificationUserPic(id,picPath);
    }
}
