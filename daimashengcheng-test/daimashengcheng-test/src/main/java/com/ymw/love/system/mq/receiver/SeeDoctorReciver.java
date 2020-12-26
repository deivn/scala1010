package com.ymw.love.system.mq.receiver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import com.ymw.love.system.entity.advisory.ShareRecordEntity;
import com.ymw.love.system.entity.faq.FaqEntity;
import com.ymw.love.system.entity.faq.FaqShareRecordEntity;
import com.ymw.love.system.entity.UHospital;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import com.ymw.love.system.entity.seeDoctor.UTakeNumber;
import com.ymw.love.system.mapper.advisory.AdvisoryMapper;
import com.ymw.love.system.mapper.advisory.ShareRecordMapper;
import com.ymw.love.system.mapper.faq.FaqMapper;
import com.ymw.love.system.mapper.faq.FaqShareRecordMapper;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mapper.seeDoctor.UTakeNumberMapper;
import com.ymw.love.system.mq.ConstantMqQueue;
import com.ymw.love.system.service.HospitalService;
import com.ymw.love.system.service.SMSSerivce;
import com.ymw.love.system.service.seeDoctor.AppointmentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("ALL")
@Component
public class SeeDoctorReciver {

    @Value("${sms.treatment.templateId}")
    private String templateId;

    @Autowired
    private UTakeNumberMapper numberMapper;
    @Autowired
    private FaqMapper faqMapper;
    @Autowired
    private FaqShareRecordMapper faqShareRecordMapper;
    @Autowired
    private AdvisoryMapper advisoryMapper;
    @Autowired
    private ShareRecordMapper shareRecordMapper;

    @Autowired
    private SMSSerivce smsUtil;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 用户预约挂号号源数减操作
     * @param takeNumber
     */
    @RabbitListener(queues = ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_SUBTRACT)
    public void subTractNumber(UTakeNumber takeNumber) {
        numberMapper.update(takeNumber, new QueryWrapper<UTakeNumber>().eq("hospital_id", takeNumber.getHospitalId())
                .eq("create_time", takeNumber.getCreateTime()));
    }

    /**
     * 用户取消挂号号源数加操作
     * @param takeNumber
     */
    @RabbitListener(queues = ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_ADD)
    public void addNumber(UTakeNumber takeNumber) {
        numberMapper.update(takeNumber, new QueryWrapper<UTakeNumber>().eq("hospital_id", takeNumber.getHospitalId())
                .eq("create_time", takeNumber.getCreateTime()));
    }

    /**
     * 问答查看源数加操作
     * @param faqEntity
     */
    @RabbitListener(queues = ConstantMqQueue.DOCTOR_ADD_FAQ_VIEW)
    public void addFaqView(FaqEntity faqEntity) {
        faqMapper.updateViewCount(faqEntity.getId());
    }
    /**
     * 问答分享记录添加操作
     * @param faqShareRecordEntity
     */
    @RabbitListener(queues = ConstantMqQueue.DOCTOR_ADD_FAQ_SHARE)
    public void addFaqShare(FaqShareRecordEntity faqShareRecordEntity) {
       faqShareRecordMapper.insert(faqShareRecordEntity);
    }

    /**
     * 咨询分享记录添加操作
     * @param advisoryEntity
     */
    @RabbitListener(queues = ConstantMqQueue.DOCTOR_ADD_ADVISORY_SHARE)
    public void addAdvisoryShare(ShareRecordEntity shareRecordEntity) {
        shareRecordMapper.insert(shareRecordEntity);
    }

    /**
     * 咨询查看源数加操作
     * @param advisoryEntity
     */
    @RabbitListener(queues = ConstantMqQueue.DOCTOR_ADD_ADVISORY_VIEW)
    public void addAdvisoryView(NewsAdvisoryEntity advisoryEntity) {
        advisoryMapper.updateViewCount(advisoryEntity.getId());
    }


    /**
     * 用户预约挂号密码短信发送
     * @param seeDoctor
     */
    @RabbitListener(queues = ConstantMqQueue.SEE_DOCTOR_MESSAGE_SEND)
    public void treatMessageSend(USeeDoctor seeDoctor) {
        StringBuffer sb = new StringBuffer();
        String phone = seeDoctor.getPatientPhone();
        //1.年月日上/下午
        String date = appointmentService.getDateDetailByTreadDate(seeDoctor.getTreatmentDate(), seeDoctor.getDayHalfStatus());
        //2.获取title
        UHospital hospitalRecord = hospitalService.getHospitalRecord(seeDoctor.getHospitalId());
        String title = hospitalRecord.getTitle();
        //3.订单号
        String orderId = seeDoctor.getOrderId();
        //4.取号密码
        String takePassword = seeDoctor.getTakePassword();
        //5.就诊人
        String patientName = seeDoctor.getPatientName();
        //6.就诊地址
        String address = hospitalRecord.getSite();
        //7.医院电话
        String hospitalPhone = hospitalRecord.getPhone();
        sb.append(date).append("||").append(title).append("||")
                .append(orderId).append("||").append(takePassword)
                .append("||").append(patientName).append("||")
                .append(address).append("||").append(hospitalPhone);
        /**
         * 	已成功预约@ @ (订单号:@， 取号密码: @，就诊人: @)，
         * 	温馨提示: 我院已实行实名制就诊，请携带身份证，
         * 	于就诊时间至@，(逾期未就诊, 此号源将作废处理) 。
         * 	如需取消请提前一天自行在类风湿互助app自行取消预约，
         * 	失约3次将影响您的预约权利。如需查询、取消订单或咨询请戳类风湿互助app：@
         */
        smsUtil.SendSms(phone, sb.toString(),templateId);// 发送验证码
    }



}
