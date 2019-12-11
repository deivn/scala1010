package com.ymw.love.system.mq.sender;

import com.ymw.love.system.mq.CommonConstantMqQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TownEnergySender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     *能量收取发送统计
     */
    public void energyFinishAdd(Map<String, Object> sendMap){
        rabbitTemplate.convertAndSend(CommonConstantMqQueue.TOWN_TASK_FINISH_ADD, sendMap);
    }

    public void energyExpired(Map<String, Object> sendMap){
        rabbitTemplate.convertAndSend(CommonConstantMqQueue.TOWN_TASK_ENERGY_EXPIRED, sendMap);
    }


}
