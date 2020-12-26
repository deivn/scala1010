package com.ymw.love.system.mq.sender;

import com.alibaba.fastjson.JSON;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import com.ymw.love.system.entity.advisory.ShareRecordEntity;
import com.ymw.love.system.entity.faq.FaqEntity;
import com.ymw.love.system.entity.faq.FaqShareRecordEntity;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import com.ymw.love.system.entity.seeDoctor.UTakeNumber;
import com.ymw.love.system.mq.ConstantMqQueue;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SeeDoctorSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 预约挂号提交后，减去对应日期的号源数减操作
     *
     */
    public void subTractNumber(UTakeNumber takeNumber){
        rabbitTemplate.convertAndSend(ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_SUBTRACT,takeNumber);
    }

    /**
     * 预约挂号提交后，减去对应日期的号源数加操作
     *
     */
    public void addNumber(UTakeNumber takeNumber){
        rabbitTemplate.convertAndSend(ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_ADD,takeNumber);
    }



    /**
     * 问答查看源数加操作
     *
     */
    public void addFaqView(FaqEntity faqEntity){
        rabbitTemplate.convertAndSend(ConstantMqQueue.DOCTOR_ADD_FAQ_VIEW,faqEntity);
    }

    /**
     * 问答分享记录添加操作
     *
     */
    public void addFaqShare(FaqShareRecordEntity faqEntity){
        rabbitTemplate.convertAndSend(ConstantMqQueue.DOCTOR_ADD_FAQ_SHARE,faqEntity);
    }

    /**
     * 咨询分享记录添加操作
     *
     */
    public void addAdvisoryShare(ShareRecordEntity shareRecordEntity){
        rabbitTemplate.convertAndSend(ConstantMqQueue.DOCTOR_ADD_ADVISORY_SHARE,shareRecordEntity);
    }

    /**
     * 咨询查看源数加操作
     *
     */
    public void addAdvisoryView(NewsAdvisoryEntity advisoryEntity){
        rabbitTemplate.convertAndSend(ConstantMqQueue.DOCTOR_ADD_ADVISORY_VIEW,advisoryEntity);
    }

    /**
     * 用户预约挂号密码短信发送
     *
     */
    public void smsSend(USeeDoctor seeDoctor){
        rabbitTemplate.convertAndSend(ConstantMqQueue.SEE_DOCTOR_MESSAGE_SEND,seeDoctor);
    }

}
