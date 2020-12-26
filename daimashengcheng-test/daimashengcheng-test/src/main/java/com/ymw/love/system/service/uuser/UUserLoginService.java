package com.ymw.love.system.service.uuser;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.entity.coupon.CouponsEntity;
import com.ymw.love.system.entity.message.input.push.MessagePushInput;
import com.ymw.love.system.entity.message.input.push.SystemMessageInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.DESStupidUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.util.VerifyUtil;
import com.ymw.love.system.vo.UUserVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月5日  类说明：用户
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class UUserLoginService extends BaseService  {

  


    @Autowired
    private UserLogInInfo userLogInInfo;

    @Value("${secret.key}")
    private String secretKey;//签名的 key 8位


    @Value("${sms.verification.templateId}")
    private String verificationTemplateId;

    @Value("${love.coupons}")
    private String loveCoupons;

    @Value("${salvation.coupons}")
    private String salvationCoupons;


    private Long tokenTime1 = 3600 * 24 * 30L;//30天
    private Long tokenTime2 = 3600 * 4L;//4小时


    /**
     * 登录
     *
     * @param code     验证码
     * @param phone    手机号码
     * @param password 密码
     * @return
     */
    public Map<String, String> logIn(String code, String phone, String password) {

        if ((StringUtils.isEmpty(code) && StringUtils.isEmpty(password)) || StringUtils.isEmpty(phone)) {
            throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
        }
        phone = phone.replace("-", "");
        if (!VerifyUtil.phoneVerify(phone)) {// 手机号码校证
            throw new MissRequiredParamException(HintTitle.System.phone_no_norm);
        }
        //查询手机号
        List<UUser> list = mf.getUUserMapper().selectList(new QueryWrapper<UUser>().eq("phone", phone));
        if (list == null || list.size() <= 0) {
            throw new MissRequiredParamException(HintTitle.User.account_password_error);
        }


        if (StringUtils.isNotEmpty(password)) { // 密码
            //密码解密
            //String pa = VerifyUtil.getMD5(decrypt(password), 1);

            String pa= VerifyUtil.getMD5(password,1);
            if (!pa.equals(list.get(0).getPassword())) {
                throw new MissRequiredParamException(SystemEnum.FAIL_USER_PASSWORD_ERROR, HintTitle.User.account_password_error);
            }


        } else if (StringUtils.isNotEmpty(code)) {// 验证码
            String codes = sf.getRedisService().getCode(Resource.verificationFront.LOG_IN.getValue() + phone);
            if (StringUtils.isEmpty(codes) || !StringUtils.Equals(code, codes)) {
                throw new MissRequiredParamException(HintTitle.User.account_code_error);
            }
            //移除验证码
            sf.getRedisService().delete(Resource.verificationFront.LOG_IN.getValue() + phone);
        }

        if (list.get(0).getState() == 2) {//禁用状态
            throw new MissRequiredParamException(SystemEnum.FAIL_USER_JY, HintTitle.User.user_jy_error);
        }

        UUserVO vo = new UUserVO(list.get(0).getId(), list.get(0).getNickName(), list.get(0).getRoleCode(),
                list.get(0).getInviteCode(), phone, list.get(0).getName(), list.get(0).getHuanxinId(), list.get(0).getJpushId(),
                list.get(0).getState(), userLogInInfo.getClientSource());

        //移除其他设备登录状态
        String token1 = getToken(list.get(0).getId(), list.get(0).getLoginTime());
        UserExit(token1);

        //app 七天有效期 ，其他4个小时
        long time = userLogInInfo.getClientSource() > 2 ? tokenTime1 : tokenTime2;

        //更新登录时间
        UUser u = new UUser();
        u.setLoginTime(new Date());
        mf.getUUserMapper().update(u, new UpdateWrapper<UUser>().eq("id", list.get(0).getId()));

        //设置token
        String token2 = getToken(list.get(0).getId(), u.getLoginTime());
        sf.getRedisService().set(Resource.enter.web_user + token2, vo, time);

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token2);
        map.put("jpushId", list.get(0).getJpushId());
        map.put("huanxinId", list.get(0).getHuanxinId());
        return map;
    }

    /**
     * 用户注册
     *
     * @param code       验证码
     * @param phone      手机号码
     * @param password   密码
     * @param inviteCode 邀请码
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> userEnroll(String code, String inviteCode, String phone, String password)  {

        if (StringUtils.isEmpty(inviteCode)) {
            throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
        }
//        //验证参数
        password = verifyNull(code, phone, password, Resource.verificationFront.ENROL);

        // 获取推荐用户
        List<UUser> list = mf.getUUserMapper().selectList(new QueryWrapper<UUser>().eq("invite_code", inviteCode).or().eq("phone", inviteCode));
        if (list == null || list.size() <= 0) {
            throw new MissRequiredParamException(HintTitle.User.user_no_invite_code);
        }

        // ②创建用户 关联数据表
        UUser user = new UUser();
        user.setId(SnowFlakeUtil.nextId(18));
        user.setPassword(VerifyUtil.getMD5(password, 1));
        user.setPhone(phone);
        user.setInviteCode(SnowFlakeUtil.getStochastic(8));
        user.setCreatesTime(new Date());
        user.setRoleCode(Resource.userRole.roleCode);
        user.setLoginTime(new Date());
        user.setJpushId("jp" + SnowFlakeUtil.getStochastic(28));//极光
        user.setNickName("爱心" + SnowFlakeUtil.getRandom(4));
        int insert = mf.getUUserMapper().insert(user);
        mf.getUUserMapper().insertUserExtend(user.getId());
        mf.getUUserMapper().insertUserPayee(user.getId(), SnowFlakeUtil.nextId(17));
        // ③建立用户关系
        mf.getUUserMapper().insertUserRelation(user.getId(), list.get(0).getId());
        if (insert > 0) {
            this.coupons(list,user.getId(),phone);
        }
        // 设置登录状态
        UUserVO vo = new UUserVO(user.getId(), null, user.getRoleCode(), user.getInviteCode(), phone, null, null, user.getJpushId(),
                1, userLogInInfo.getClientSource());

        //app 七天有效期 ，其他4个小时
        long time = userLogInInfo.getClientSource() > 2 ? tokenTime1 : tokenTime2;
        String token = getToken(user.getId(), user.getLoginTime());
        sf.getRedisService().set(Resource.enter.web_user + token, vo, time);


        //发送对应mq、环信
        // uUserSender.uUserEnroll(user.getId(), user.getNickName());

        //移除验证码
        sf.getRedisService().delete(Resource.verificationFront.ENROL.getValue() + phone);

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("jpushId", user.getJpushId());
        return map;
    }

    /**
     * 优惠券
     */
   private void coupons(List<UUser> list, String userId,String phone) {
       try {
           //救助券发放
           CouponsEntity couponsEntity=new CouponsEntity();
           couponsEntity.setAddTime(LocalDateTime.now());
           couponsEntity.setMoney(Integer.valueOf(salvationCoupons));
           couponsEntity.setCouponsType(1);
           couponsEntity.setUserId(userId);
           Integer integer = mf.getInvitationCodeMapper().insert(couponsEntity);
           if (integer>0){
               //消息推送
               MessagePushInput pushInput=new MessagePushInput();
               pushInput.setOption(Constant.MESSAGE_SYSTEM_SALAVATION);
               pushInput.setDestUid(userId);
               pushInput.setSystemInput(new SystemMessageInput(phone,salvationCoupons));
               pushInput.setBusinessId(couponsEntity.getId());
               Result result = sf.getMessagePushService().optionPush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, pushInput);
           }
           //爱心券发放
           CouponsEntity couponsLove=new CouponsEntity();
           couponsLove.setUserId(list.get(0).getId());
           couponsLove.setCouponsType(2);
           couponsLove.setMoney(Integer.valueOf(loveCoupons));
           couponsLove.setAddTime(LocalDateTime.now());
           couponsLove.setSalvationId(couponsEntity.getId());
           int insCouponsLove = mf.getInvitationCodeMapper().insert(couponsLove);
           if (insCouponsLove>0){
               MessagePushInput pushInput=new MessagePushInput();
               pushInput.setOption(Constant.MESSAGE_SYSTEM_LOVE);
               pushInput.setDestUid(list.get(0).getId());
               pushInput.setSystemInput(new SystemMessageInput(phone,loveCoupons));
               pushInput.setBusinessId(couponsLove.getId());
               Result result = sf.getMessagePushService().optionPush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, pushInput);
           }
       }catch (Exception x){
           throw new MissRequiredParamException("优惠券发送失败."+x.getMessage());
       }

    }

    /**
     * 验证邀请码和手机号码是否存在
     *
     * @param code
     */
    public String inviteCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
        }
        List<UUser> list = mf.getUUserMapper().selectList(new QueryWrapper<UUser>().eq("invite_code", code).or().eq("phone", code));
        if (list == null || list.size() <= 0) {
            throw new MissRequiredParamException(HintTitle.User.user_no_invite_code);
        }
        return list.get(0).getInviteCode();
    }

    /**
     * 发送验证码 注册、登录、找回密码
     *
     * @param phone
     */
    public void sendCode(String phone, Integer type) {

        if (StringUtils.isEmpty(phone) || type == null) {
            throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
        }

        phone = phone.replace("-", "");
        if (!VerifyUtil.phoneVerify(phone)) {// 手机号码校证
            throw new MissRequiredParamException(HintTitle.System.phone_no_norm);
        }
        StringBuffer sb = new StringBuffer();

        // 判断手机号码是否存在
        int i = mf.getUUserMapper().selectCount(new QueryWrapper<UUser>().eq("phone", phone));

        if (Resource.verificationFront.ENROL.getKey().equals(type)) {// 注册
            sb.append(Resource.verificationFront.ENROL.getValue());

            if (i > 0) {// 用户存在
                throw new MissRequiredParamException(HintTitle.User.user_account_exist);
            }

        } else if (Resource.verificationFront.LOG_IN.getKey().equals(type)) {// 登录
            sb.append(Resource.verificationFront.LOG_IN.getValue());
            if (i <= 0) {// 用户不存在
                throw new MissRequiredParamException(HintTitle.User.user_phone_no_enroll);
            }
        } else if (Resource.verificationFront.FIND_PASSWORD.getKey().equals(type)) {// 找回密码
            sb.append(Resource.verificationFront.FIND_PASSWORD.getValue());
            if (i <= 0) {// 用户不存在
                throw new MissRequiredParamException(HintTitle.User.user_phone_no_enroll);
            }
        } else {// 错误类型
            throw new MissRequiredParamException(HintTitle.System.error_type);

        }

        String code = SnowFlakeUtil.getRandom(6) + "";
        sb.append(phone);
        Map<String, Object> map = sf.getSMSSerivce().SendSms(phone, code, verificationTemplateId);// 发送验证码
        if (map.get("Code").toString().equals("0")) {
            sf.getRedisService().setCode(sb.toString(), code, 3 * 60L);
        } else {
            throw new MissRequiredParamException(HintTitle.System.failed);
        }

    }

    /**
     * 退出
     */
    public void UserExit(String token) {

        if (StringUtils.isEmpty(token)) {
            token = userLogInInfo.getToken();
        }
        sf.getRedisService().delete(Resource.enter.web_user + token);
    }

    /**
     * 找回密码
     *
     * @param code
     * @param phone
     * @param password
     */
    public void findRestorePassword(String code, String phone, String password) {
        password = verifyNull(code, phone, password, Resource.verificationFront.FIND_PASSWORD);

        List<UUser> list = mf.getUUserMapper().selectList(new QueryWrapper<UUser>().eq("phone", phone));
        if (list == null || list.size() != 1) {
            throw new MissRequiredParamException(HintTitle.User.user_phone_no_enroll);
        }

        password = VerifyUtil.getMD5(password, 1);

        UUser u = new UUser();
        u.setPassword(password);
        mf.getUUserMapper().update(u, new UpdateWrapper<UUser>().eq("phone", phone));
        //移除验证码
        sf.getRedisService().delete(Resource.verificationFront.FIND_PASSWORD.getValue() + phone);
    }


    /*
     * 验证参数
     */
    private String verifyNull(String code, String phone, String password, Resource.verificationFront rv) {
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
        }

        //密码解密
       // password = decrypt(password);

        // 验证密码位数
        if (password.trim().length() < 6) {
            throw new MissRequiredParamException(HintTitle.User.password_digit_error);
        }

        //短信验证码
        String c = sf.getRedisService().getCode(rv.getValue() + phone);
        if (StringUtils.isEmpty(c)) {
            throw new MissRequiredParamException(HintTitle.User.verification_code_past);
        }

        if (!StringUtils.Equals(c, code)) {
            throw new MissRequiredParamException(HintTitle.User.verification_code_error);
        }

        return password;
    }


    /**
     * 解密
     *
     * @param arg
     * @return
     */
    private String decrypt(String arg) {
        try {
            return DESStupidUtil.decrypt(arg, secretKey);
        } catch (Exception e) {
            throw new MissRequiredParamException(HintTitle.System.parse_error);
        }
    }

    private String getToken(String id, Date date) {
        return VerifyUtil.getMD5(id + DateUtil.parseDateToStr(date, DateUtil.DATE_FORMAT_YYYYMMDDHHmm), 1);
    }

}
