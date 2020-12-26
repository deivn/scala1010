package com.ymw.love.system.service;

import com.ymw.love.system.service.advisory.AdvisoryServiceImpl;
import com.ymw.love.system.service.banner.BannerServiceImpl;
import com.ymw.love.system.service.coupon.InvitationCodeService;
import com.ymw.love.system.service.faq.FaqServiceImpl;
import com.ymw.love.system.service.message.MessageManagerService;
import com.ymw.love.system.service.posters.PostersService;
import com.ymw.love.system.service.seeDoctor.AppointmentService;
import com.ymw.love.system.service.seeDoctor.RegistManagerService;
import com.ymw.love.system.service.seeDoctor.TakeNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.redis.RedisService;
import com.ymw.love.system.service.auser.AUserService;
import com.ymw.love.system.service.message.MessagePushService;
import com.ymw.love.system.service.test.impl.TestServiceImpl;
import com.ymw.love.system.service.uuser.UUserLoginService;
import com.ymw.love.system.service.uuser.UUserService;

import lombok.Getter;

@Getter
@Service
public class ServiceFactory {

    @Autowired
    private TestServiceImpl testService;

    
    @Autowired
    private DictService dictService;
    
    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private HuanxiService huanxiService;
    
    @Autowired
    private OssFileService ossFileService;
    
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    
    
    @Autowired
    private SMSSerivce sMSSerivce;
    
    @Autowired
    private SysLogService sysLogService;
    
    @Autowired
    private UUserLoginService uUserLoginService;
    
    @Autowired
    private UUserService uUserService;
    
    @Autowired
    private AUserService aUserService;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private MessagePushService messagePushService;
    
    @Autowired
    private UserLogInInfo userLogInInfo;

    @Autowired
    private MessagePushService pushService;

    @Autowired
    private BannerServiceImpl bannerService;

    @Autowired
    private AdvisoryServiceImpl advisoryService;

    @Autowired
    private InvitationCodeService codeService;

    @Autowired
    private FaqServiceImpl faqService;

    @Autowired
    private MessageManagerService managerService;



    @Autowired
    private PostersService postersService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private RegistManagerService registManagerService;

    @Autowired
    private TakeNumberService takeNumberService;


}
