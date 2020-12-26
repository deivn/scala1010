package com.ymw.love.system.mq.sender;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.mq.ConstantMqQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     *消息推送
     */
    public void messagePush(UMessagePush messagePush){
        rabbitTemplate.convertAndSend(ConstantMqQueue.MESSAGE_PUSH, messagePush);
    }

}
