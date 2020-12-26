package com.ymw.love.system.mapper;

import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.mapper.advisory.*;
import com.ymw.love.system.mapper.banner.BannerMapper;
import com.ymw.love.system.mapper.coupon.UserExtendMapper;
import com.ymw.love.system.mapper.coupon.WithdrawMapper;
import com.ymw.love.system.mapper.coupon.WithdrawRecordMapper;
import com.ymw.love.system.mapper.faq.*;
import com.ymw.love.system.mapper.message.UMessageCategoryMapper;
import com.ymw.love.system.mapper.message.UMessageConfigMapper;
import com.ymw.love.system.mapper.message.UMessagePushMapper;
import com.ymw.love.system.mapper.posters.PostersManagerMapper;
import com.ymw.love.system.mapper.posters.UPostersMapper;
import com.ymw.love.system.mapper.posters.UPostersShareMapper;
import com.ymw.love.system.mapper.seeDoctor.*;
import com.ymw.love.system.mq.sender.PostersCaculateSender;
import com.ymw.love.system.mq.sender.SeeDoctorSender;
import com.ymw.love.system.service.sensitive.SensitiveImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ymw.love.system.mapper.coupon.InvitationCodeMapper;
import com.ymw.love.system.mapper.message.MessageManagerMapper;
import com.ymw.love.system.mapper.sys.SysAuthorityMapper;
import com.ymw.love.system.mapper.sys.SysDictMapper;
import com.ymw.love.system.mapper.sys.SysLogMapper;
import com.ymw.love.system.mapper.sys.SysRoleAuthorityMapper;
import com.ymw.love.system.mapper.sys.SysRoleMapper;
import com.ymw.love.system.mapper.test.TestMapper;

import lombok.Getter;

/**
 * Mapper 工厂，
 */
@SuppressWarnings("ALL")
@Getter
@Service
public class MapperFactory {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private MessageManagerMapper messageManagerMapper;
    
    
    @Autowired
    private AUserMapper aUserMapper;
    
    @Autowired
    private UUserMapper   uUserMapper;
    
    
    @Autowired
    private UHospitalMapper uHospitalMapper;
    
    @Autowired
    private SysAuthorityMapper sysAuthorityMapper;
    
    @Autowired
    private SysDictMapper sysDictMapper;
    
    @Autowired
    private SysLogMapper sysLogMapper;
    
    @Autowired
    private SysRoleAuthorityMapper sysRoleAuthorityMapper;
    
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private InvitationCodeMapper invitationCodeMapper;
    
    @Autowired
    private UPatientEvaluationMapper uPatientEvaluationMapper;

    @Autowired
    private UserLogInInfo userLogInInfo;
    @Autowired
    private AdvisoryTypeMapper advisoryTypeMapper;
    @Autowired
    private AdvisoryCommentMapper advisoryCommentMapper;
    @Autowired
    private AdvisoryMapper advisoryMapper;
    @Autowired
    private CommentGiveMapper commentGiveMapper;
    @Autowired
    private AdvisoryCollectMapper advisoryCollectMapper;
    @Autowired
    private AdvisoryGiveMapper advisoryGiveMapper;
    @Autowired
    private ShareRecordMapper shareRecordMapper;
    @Autowired
    private AdvisoryDraftsMapper draftsMapper;
    @Autowired
    private SeeDoctorSender seeDoctorSender;
    @Autowired
    private SensitiveImpl sensitive;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private PostersCaculateSender postersCaculateSender;


    @Autowired
    private UserExtendMapper userExtendMapper;
    @Autowired
    private WithdrawMapper withdrawMapper;

    @Autowired
    private WithdrawRecordMapper withdrawRecordMapper;

    @Autowired
    private FaqTypeMapper faqTypeMapper;
    @Autowired
    private FaqCommentMapper faqCommentMapper;

    @Autowired
    private FaqCollectMapper faqCollectMapper;
    @Autowired
    private FaqShareRecordMapper faqShareRecordMapper;

    @Autowired
    private FaqSpecialMapper specialMapper;

    @Autowired
    private FaqMapper faqMapper;
    @Autowired
    private FaqCommentGiveMapper faqCommentGiveMapper;




    @Autowired
    private MessageManagerMapper managerMapper;

    @Autowired
    private UMessageCategoryMapper categoryMapper;

    @Autowired
    private UMessageConfigMapper configMapper;

    @Autowired
    private UMessagePushMapper messagePushMapper;

    @Autowired
    private PostersManagerMapper postersManagerMapper;

    @Autowired
    private UPostersMapper postersMapper;

    @Autowired
    private UPostersShareMapper postersShareMapper;

    @Autowired
    private RegistManagerMapper registManagerMapper;

    @Autowired
    private SeeDoctorQueryResultMapper seeDoctorQueryResultMapper;

    @Autowired
    private TakeNumberQueryResultMapper numberQueryResultMapper;

    @Autowired
    private UPatientEvaluationMapper patientEvaluationMapper;

    @Autowired
    private USeeDoctorMapper seeDoctorMapper;

    @Autowired
    private UTakeNumberMapper takeNumberMapper;



}
