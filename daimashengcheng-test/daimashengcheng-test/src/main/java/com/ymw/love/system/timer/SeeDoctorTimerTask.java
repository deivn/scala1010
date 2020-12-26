package com.ymw.love.system.timer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mapper.seeDoctor.USeeDoctorMapper;
import com.ymw.love.system.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.Resource;
import java.util.Date;

@Configuration
@EnableScheduling // 启用定时任务
public class SeeDoctorTimerTask {

    private static final Logger log = LoggerFactory.getLogger(SeeDoctorTimerTask.class);

    @Resource
    private USeeDoctorMapper seeDoctorMapper;

    @Scheduled(cron = "0 55 23 * * ?")
    public void seeDoctorScheduler() {
        log.info("--------------调度开始执行，用户预约未就诊状态更新状态为已过期----------");
        log.info("--------------"+ DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)+"--开始时间执行任务------------");
        long start = System.currentTimeMillis();
        USeeDoctor seeDoctor = new USeeDoctor();
        seeDoctor.setOptionStatus(Constant.EXPIRED_SEE_DOCTOR);
        seeDoctorMapper.update(seeDoctor, new QueryWrapper<USeeDoctor>()
                .eq("option_status", Constant.PRE_SEE_DOCTOR)
                .eq("treatment_date", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD)));
        log.info("--------------"+DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)+"-----调度结束-----------");
        long end = System.currentTimeMillis();
        log.info("----------------共用时:"+(end-start)/1000+" 秒----------");
    }
}
