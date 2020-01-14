package com.ymw.love.system.mq.sender;

import com.ymw.love.system.mq.CommonConstantMqQueue;
import com.ymw.love.system.mq.entity.UMessagePush;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     *消息推送
     */
    public void messagePush(UMessagePush messagePush){
        rabbitTemplate.convertAndSend(CommonConstantMqQueue.MESSAGE_PUSH, messagePush);
    }

    /**
     * 能量过期消息推送，用户每天首次打开APP，推送一次
     */
    public void energyExpiredSignal(String uid) {
        rabbitTemplate.convertAndSend(CommonConstantMqQueue.ENERGY_EXPIRED_SIGNAL, uid);
    }

}
