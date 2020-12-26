package com.ymw.love.system.mq.sender;
import com.ymw.love.system.entity.banner.BannerEntity;
import com.ymw.love.system.mq.ConstantMqQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PostersCaculateSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     *海报分享统计
     */
    public void sharedCaculate(Map<String, Object> map){
        rabbitTemplate.convertAndSend(ConstantMqQueue.POSTERS_SHARED_CACULATE,map);
    }

    /**
     *
     * banner点击量统计
     */
    public void bannerClickCount(BannerEntity bannerEntity){
        rabbitTemplate.convertAndSend(ConstantMqQueue.POSTERS_SHARED_CACULATE,bannerEntity);
    }

}
