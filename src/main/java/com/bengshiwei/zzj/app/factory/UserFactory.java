package com.bengshiwei.zzj.app.factory;

import com.bengshiwei.zzj.app.bean.base.ResponseModel;
import com.bengshiwei.zzj.app.bean.card.UserCard;
import com.bengshiwei.zzj.app.bean.db.FeedBack;
import com.bengshiwei.zzj.app.bean.db.User;
import com.bengshiwei.zzj.app.bean.db.UserFollow;
import com.bengshiwei.zzj.app.utils.Hib;
import com.google.common.base.Strings;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息的逻辑处理
 * Created by JamesZhang on 2018/5/17.
 */
public class UserFactory {


    /**
     * 修改用户头像逻辑处理
     * @param userId
     * @param picPath
     * @return
     */
    public static ResponseModel modificationUserPic(String userId,String picPath){
        //查找到当前用户添加路径修改
        User user = findById(userId);
        if(user!=null){
            user.setPortrait(picPath);
            Session session = Hib.session();
            //开启一个事物
            session.beginTransaction();
            try{
                //保存操作
                session.saveOrUpdate(user);
                //提交事物
                session.getTransaction().commit();

            }catch (Exception e){
                //如果失败情况下事件回滚
                session.getTransaction().rollback();

                return ResponseModel.buildRegisterError();
            }
        }else {
            return ResponseModel.buildNotFoundUserError(null);
        }
        return ResponseModel.buildOk(new UserCard(user));
    }


    /**
     * 搜索联系人的实现
     *
     * @param name 查询的name，允许为空
     * @return 查询到的用户集合，如果name为空，则返回最近的用户
     */
    @SuppressWarnings("unchecked")
    public static List<User> search(String name) {
        if (Strings.isNullOrEmpty(name))
            name = ""; // 保证不能为null的情况，减少后面的一下判断和额外的错误
        final String searchName = "%" + name + "%"; // 模糊匹配

        return Hib.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return (List<User>) session.createQuery("from User where lower(name) like :name ")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 至多20条
                    .list();

        });

    }

    /**
     * 关注人的操作
     *
     * @param origin 发起者
     * @param target 被关注的人
     * @param alias  备注名
     * @return 被关注的人的信息
     */
    public static User follow(final User origin, final User target, final String alias) {
        UserFollow follow = getUserFollow(origin, target);
        if (follow != null) {
            // 已关注，直接返回
            return follow.getTarget();
        }

        return Hib.query(session -> {
            // 想要操作懒加载的数据，需要重新load一次
            session.load(origin, origin.getId());
            session.load(target, target.getId());

            // 我关注人的时候，同时他也关注我，
            // 所有需要添加两条UserFollow数据
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);
            // 备注是我对他的备注，他对我默认没有备注
            originFollow.setAlias(alias);

            // 发起者是他，我是被关注的人的记录
            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            // 保存数据库
            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });
    }

    /**
     * 获取我的联系人的列表
     *
     * @param self User
     * @return List<User>
     */
    public static List<User> contacts(User self) {
        return Hib.query(session -> {
            // 重新加载一次用户信息到self中，和当前的session绑定
            session.load(self, self.getId());

            // 获取我关注的人
            Set<UserFollow> flows = self.getFollowing();

            // 使用简写方式
            return flows.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());
        });
    }
    /**
     * 查询两个人是否已经关注
     *
     * @param origin 发起者
     * @param target 被关注人
     * @return 返回中间类UserFollow
     */
    public static UserFollow getUserFollow(final User origin, final User target) {
        return Hib.query(session -> (UserFollow) session
                .createQuery("from UserFollow where originId = :originId and targetId = :targetId")
                .setParameter("originId", origin.getId())
                .setParameter("targetId", target.getId())
                .setMaxResults(1)
                // 唯一查询返回
                .uniqueResult());
    }

    // 通过Phone找到User
    public static User findByName(String phone) {
        return Hib.query(session -> {
            return (User) session
                    .createQuery("from User where name=:name")
                    .setParameter("name", phone)
                    .uniqueResult();
        });
    }


    // 通过Name找到User
    public static User findById(String id) {
        // 通过Id查询，更方便
        return Hib.query(session -> session.get(User.class, id));
    }


    /**
     * 反馈建议接口
     * @param content 内容
     * @param contact 联系方式
     * @return
     */
    public static ResponseModel feedBack(String content,String contact){

        if(content == null||content.equals("")){
            return ResponseModel.buildMessage(ResponseModel.FAILS,"反馈内容不能为空");
        }

        FeedBack feedBack = new FeedBack();
        feedBack.setContact(contact);
        feedBack.setContent(content);
        Session session = Hib.session();
        //开启一个事物
        session.beginTransaction();
        try{
            //保存操作
            session.save(feedBack);
            //提交事物
            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            //如果失败情况下事件回滚
            session.getTransaction().rollback();
            return ResponseModel.buildMessage(ResponseModel.FAILS,"提交失败");
        }

        return ResponseModel.buildOk();
    }
}
