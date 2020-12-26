package com.ymw.love.system.mq.receiver;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ymw.love.system.common.JpushNotification;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.entity.message.UMessageConfig;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mapper.UUserMapper;
import com.ymw.love.system.mapper.message.UMessageConfigMapper;
import com.ymw.love.system.mapper.message.UMessagePushMapper;
import com.ymw.love.system.mq.ConstantMqQueue;
import com.ymw.love.system.service.JpushSerivce;
import com.ymw.love.system.service.message.MessageManagerService;
import com.ymw.love.system.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Component
public class MessagePushReciver {

    private static final Logger log = LoggerFactory.getLogger(MessagePushReciver.class);

    @Autowired
    private UMessagePushMapper messagePushMapper;

    @Autowired
    private MessageManagerService managerService;

    @Autowired
    private UUserMapper userMapper;

    @Autowired
    private JpushSerivce jpushSerivce;

    /**
     * 统一消息推送
     * @param takeNumber
     */
    @RabbitListener(queues = ConstantMqQueue.MESSAGE_PUSH)
    public void messageCustomer(UMessagePush messagePush) {
        //极光推送逻辑
        log.info("极光推送---------------------------"+ JSON.toJSONString(messagePush));
        Integer subType = messagePush.getSubType();
        int type = messagePush.getType();
        //全平台消息推送
        if(type == Constant.OPTION_MESSAGE_ACTIVITY_PUSH){
            //消息批量入库
            bathMessageInsert(messagePush.getBusinessId());
            //广播
            jpushSerivce.sendPushAlias(buildNotificationExtra(messagePush), 1);
        }else {
            //消息入库
            messagePushMapper.insert(messagePush);
            //按别名推送
            if(subType != Constant.MESSAGE_DYNAMIC_COMMENT_REPLY){
                jpushSerivce.sendPushAlias(buildNotificationExtra(messagePush), 2);
            }
        }
    }

    /**
     * 活动消息
     * @param messageId
     */
    public void bathMessageInsert(String messageId){
        List<UMessagePush> messagePushes = new ArrayList<UMessagePush>();
        //全平台用户消息推送
        List<UUser> users = userMapper.selectList(new QueryWrapper<UUser>());
        if(users != null && users.size() > 0){
            UMessageConfig messageConfig = managerService.getRecordById(messageId);
            for(int i = 0; i< users.size(); i++){
                UMessagePush messagePush = new UMessagePush(Constant.OPTION_MESSAGE_ACTIVITY_PUSH,
                        Constant.OPTION_MESSAGE_ACTIVITY_PUSH, messageId, users.get(i).getId(), messageConfig.getTitle(), messageConfig.getActivityInfo());
                messagePushes.add(messagePush);
            }
        }
        if(messagePushes.size() > 0){
            for(int i = 0; i < messagePushes.size(); i++){
                messagePushMapper.insert(messagePushes.get(i));
            }
        }
    }

    public JpushNotification buildNotificationExtra(UMessagePush messagePush){
        JpushNotification notificationExtra = new JpushNotification();
        List<String> alias = new ArrayList<String>();
        if(StringUtils.isNotEmpty(messagePush.getDestUid())){
            UUser userInfo = userMapper.selectList(new QueryWrapper<UUser>().eq("id", messagePush.getDestUid())).get(0);
            alias.add(userInfo.getJpushId());
        }

//        alias.add("jp5c09d3a4cfb4444bac857ccd2e5b");
        notificationExtra.setAliases(alias);
        List<String> tags = new ArrayList<String>();
        tags.add("1");
        notificationExtra.setTags(tags);
        Map<String, String> extras = new HashMap<String, String>();
        extras.put("message_code", "");
        notificationExtra.setExtras(extras);
        notificationExtra.setAlert(messagePush.getContent());
        notificationExtra.setTitle(messagePush.getTitle());
        return notificationExtra;
    }


}
