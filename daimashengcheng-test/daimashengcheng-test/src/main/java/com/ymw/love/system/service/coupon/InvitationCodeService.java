package com.ymw.love.system.service.coupon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.entity.coupon.CouponsEntity;
import com.ymw.love.system.entity.coupon.UserExtendEntity;
import com.ymw.love.system.entity.coupon.WithdrawMoneyEntity;
import com.ymw.love.system.entity.coupon.WithdrawRecordEntity;
import com.ymw.love.system.entity.message.input.push.MessagePushInput;
import com.ymw.love.system.entity.message.input.push.SystemMessageInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.UUserVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @date 2019年8月26日14:05:26
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class InvitationCodeService extends BaseService {


    @Value("${love.coupons}")
    private String loveCoupons;

    @Value("${salvation.coupons}")
    private String salvationCoupons;


    public Result getInviteCode() throws Exception {
        try {
            Map<String, Object> map = new HashMap<>(16);
            UUserVO uUserVO = mf.getUserLogInInfo().getWebUser();
            UUser user = mf.getUUserMapper().selectById(uUserVO.getId());
            map.put("code", user.getInviteCode());
            return new Result(map);
        } catch (Exception x) {
            throw new Exception("获取邀请码异常." + x.getMessage());
        }
    }

    public Result findCouponsList(CouponsEntity couponsEntity) throws Exception {
        try {
            couponsEntity.setUserId(mf.getUserLogInInfo().getWebUser().getId());
            IPage<CouponsEntity> page = new Page<>(couponsEntity.getPageSum(), couponsEntity.getPageSize());
            IPage<CouponsEntity> couponsList = mf.getInvitationCodeMapper().findCouponsList(page, couponsEntity);
            return new Result(couponsList);
        } catch (Exception x) {
            throw new Exception("查询我的优惠券列表异常." + x.getLocalizedMessage());
        }
    }


    public Result findCouponsTypeList(CouponsEntity couponsEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(couponsEntity.getCouponsStatus())) {
                throw new Exception("参数异常.");
            }
            IPage<CouponsEntity> page = new Page<>(couponsEntity.getPageSum(), couponsEntity.getPageSize());
            LambdaQueryWrapper<CouponsEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(CouponsEntity::getUserId, mf.getUserLogInInfo().getWebUser().getId()).eq(CouponsEntity::getCouponsType, couponsEntity.getCouponsType())
                    .orderByAsc(CouponsEntity::getCouponsStatus);
            IPage<CouponsEntity> selectPage = mf.getInvitationCodeMapper().selectPage(page, lambdaQueryWrapper);
            return new Result(selectPage);
        } catch (Exception x) {
            throw new Exception("查询优惠券列表异常." + x.getLocalizedMessage());
        }
    }

    public Result findLoveCouponsList(CouponsEntity couponsEntity) throws Exception {
        try {
            IPage<CouponsEntity> page = new Page<>(couponsEntity.getPageSum(), couponsEntity.getPageSize());
            couponsEntity.setUserId(mf.getUserLogInInfo().getWebUser().getId());
            IPage<CouponsEntity> loveCouponsList = mf.getInvitationCodeMapper().findLoveCouponsList(page, couponsEntity);
            Map<String, Object> map = new HashMap<>(5);
            map.put("loveCouponsList", loveCouponsList);
            map.put("totalSum", mf.getInvitationCodeMapper().findLoveTotalSum(couponsEntity));
            return new Result(map);
        } catch (Exception x) {
            throw new Exception("查询优惠券列表异常." + x.getLocalizedMessage());
        }
    }

    public Result findUserWithdrawMoney(UserExtendEntity userExtendEntity) throws Exception {
        try {
            UserExtendEntity userExtendEntity1 = mf.getUserExtendMapper().selectById(mf.getUserLogInInfo().getWebUser().getId());
            return new Result(userExtendEntity1);
        } catch (Exception x) {
            throw new Exception(x.getLocalizedMessage());
        }
    }

    public Result findWebWithdrawList(WithdrawRecordEntity withdrawRecordEntity) throws Exception {
        try {
            IPage<WithdrawRecordEntity> page = new Page<>(withdrawRecordEntity.getPageSum(), withdrawRecordEntity.getPageSize());
            LambdaQueryWrapper<WithdrawRecordEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(WithdrawRecordEntity::getUserId, mf.getUserLogInInfo().getWebUser().getId()).orderByDesc(WithdrawRecordEntity::getAddTime);
            IPage<WithdrawRecordEntity> recordEntityIPage = mf.getWithdrawRecordMapper().selectPage(page, lambdaQueryWrapper);
            return new Result(recordEntityIPage);
        } catch (Exception x) {
            throw new Exception("提现记录." + x.getLocalizedMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insUserWithdraw(WithdrawMoneyEntity moneyEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(moneyEntity.getMoney()) || StringUtils.isEmpty(moneyEntity.getAccount()) || StringUtils.isEmpty(moneyEntity.getUserName())) {
                throw new Exception("参数异常.");
            }
            UserExtendEntity userExtendEntity1 = mf.getUserExtendMapper().selectById(mf.getUserLogInInfo().getWebUser().getId());
            if (userExtendEntity1.getMoney() < 200) {
                throw new Exception("余额不足.");
            }
            moneyEntity.setUserId(mf.getUserLogInInfo().getWebUser().getId());
            moneyEntity.setAddTime(LocalDateTime.now());
            moneyEntity.setPhone(mf.getUserLogInInfo().getWebUser().getPhone());
            int insert = mf.getWithdrawMapper().insert(moneyEntity);
            if (insert > 0) {
                WithdrawRecordEntity recordEntity = new WithdrawRecordEntity();
                recordEntity.setBillingMethod(moneyEntity.getBillingMethod());
                recordEntity.setUserId(mf.getUserLogInInfo().getWebUser().getId());
                recordEntity.setMoney(moneyEntity.getMoney());
                recordEntity.setAddTime(LocalDateTime.now());
                recordEntity.setStatus(1);
                recordEntity.setAccount(moneyEntity.getAccount());
                int insert1 = mf.getWithdrawRecordMapper().insert(recordEntity);
                log.info("提现记录--------------" + String.valueOf(insert1));
                LambdaQueryWrapper<CouponsEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(CouponsEntity::getUserId, mf.getUserLogInInfo().getWebUser().getId()).eq(CouponsEntity::getCouponsType, 2).eq(CouponsEntity::getCouponsStatus, 2);
                List<CouponsEntity> couponsEntities = mf.getInvitationCodeMapper().selectList(lambdaQueryWrapper);
                double money = 0.00;
                if (moneyEntity.getMoney() % 200 == 0) {
                    for (int i = 0; i < couponsEntities.size(); i++) {
                        money += couponsEntities.get(i).getMoney();
                        CouponsEntity couponsEntity = new CouponsEntity();
                        couponsEntity.setId(couponsEntities.get(i).getId());
                        couponsEntity.setCouponsStatus(3);
                        int updateById = mf.getInvitationCodeMapper().updateById(couponsEntity);
                        if (updateById > 0) {
                            if (money == moneyEntity.getMoney()) {
                                break;
                            }
                        }
                    }
                } else {
                    throw new Exception("提现金额有误.");
                }
                Integer subtractionMoney = mf.getUUserMapper().updSubtractionMoney(moneyEntity.getMoney(), mf.getUserLogInInfo().getWebUser().getId());
                if (subtractionMoney <= 0) {
                    throw new Exception("提现失败.");
                }
            }
            return new Result().setCode(insert <= 0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert <= 0 ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getLocalizedMessage());
        }
    }

    public Result findHxList(CouponsEntity couponsEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(couponsEntity.getCouponsType()) || StringUtils.isEmpty(couponsEntity.getCouponsStatus())) {
                throw new Exception("参数异常.");
            }
            if (mf.getUserLogInInfo().getAdminUser().getDataTier() == 2) {
                couponsEntity.setBranchId(mf.getUserLogInInfo().getAdminUser().getHospitalId());
            }
            IPage<Map<String, Object>> page = new Page<>(couponsEntity.getPageSum(), couponsEntity.getPageSize());
            IPage<Map<String, Object>> hxList = null;
            if (couponsEntity.getCouponsStatus() == 1) {
                couponsEntity.setOptionStatus(1);
                hxList = mf.getInvitationCodeMapper().findHxList(page, couponsEntity);
            } else {
                couponsEntity.setOptionStatus(2);
                hxList = mf.getInvitationCodeMapper().findYHxList(page, couponsEntity);
            }

            return new Result(hxList);
        } catch (Exception x) {
            throw new Exception("核销列表." + x.getLocalizedMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updSysHx(CouponsEntity couponsEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(couponsEntity.getId())) {
                throw new Exception("参数异常.");
            }
            couponsEntity.setHxBranch(mf.getUserLogInInfo().getAdminUser().getHospitalId());
            couponsEntity.setHxPerson(mf.getUserLogInInfo().getAdminUser().getName());
            couponsEntity.setHxTime(LocalDateTime.now());
            couponsEntity.setCouponsStatus(3);
            boolean updateById = couponsEntity.updateById();
            if (updateById) {
                CouponsEntity entity = mf.getInvitationCodeMapper().findloveList(couponsEntity.getId());
                if (entity != null) {
                    LambdaUpdateWrapper<CouponsEntity> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
                    lambdaUpdateWrapper.set(CouponsEntity::getCouponsStatus, 2).eq(CouponsEntity::getId, entity.getId());
                    int update = mf.getInvitationCodeMapper().update(null, lambdaUpdateWrapper);
                    Integer money = mf.getUUserMapper().updUserMoney(Integer.valueOf(loveCoupons), entity.getUserId());
                    Integer totalMoney = mf.getUUserMapper().updUserTotalMoney(Integer.valueOf(loveCoupons), entity.getUserId());
                    if (update > 0) {
                        MessagePushInput pushInput = new MessagePushInput();
                        pushInput.setOption(Constant.MESSAGE_SYSTEM_LOVE_ACTIVATION);
                        pushInput.setDestUid(entity.getUserId());
                        pushInput.setSystemInput(new SystemMessageInput(entity.getPhone(), loveCoupons));
                        pushInput.setBusinessId(entity.getId());
                        Result result = sf.getMessagePushService().optionPush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, pushInput);
                    }
                }
            }
            return new Result(updateById);
        } catch (Exception x) {
            throw new Exception("核销" + x.getLocalizedMessage());
        }
    }

    public Result findWithdrawList(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(withdrawMoneyEntity.getStatus())) {
                throw new Exception("参数异常.");
            }
            IPage<WithdrawMoneyEntity> page = new Page<>(withdrawMoneyEntity.getPageSum(), withdrawMoneyEntity.getPageSize());
            IPage<WithdrawMoneyEntity> hxList = mf.getInvitationCodeMapper().findWithdrawList(page, withdrawMoneyEntity);
            return new Result(hxList);
        } catch (Exception x) {
            throw new Exception("sys提现列表." + x.getLocalizedMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updWithdraw(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(withdrawMoneyEntity.getId())) {
                throw new Exception("参数异常.");
            }
            withdrawMoneyEntity.setWithdrawTime(LocalDateTime.now());
            withdrawMoneyEntity.setAdminName(mf.getUserLogInInfo().getAdminUser().getName());
            withdrawMoneyEntity.setStatus(2);
            boolean updateById = withdrawMoneyEntity.updateById();
            if (updateById) {
                WithdrawMoneyEntity moneyEntity = mf.getWithdrawMapper().selectById(withdrawMoneyEntity.getId());
                WithdrawRecordEntity recordEntity = new WithdrawRecordEntity();
                recordEntity.setBillingMethod(moneyEntity.getBillingMethod());
                recordEntity.setUserId(moneyEntity.getUserId());
                recordEntity.setMoney(moneyEntity.getMoney());
                recordEntity.setWithdrawTime(LocalDateTime.now());
                recordEntity.setStatus(2);
                recordEntity.setAccount(moneyEntity.getAccount());
                int insert = mf.getWithdrawRecordMapper().insert(recordEntity);
                log.info("提现记录--------------" + String.valueOf(insert));
                MessagePushInput pushInput = new MessagePushInput();
                pushInput.setOption(Constant.MESSAGE_SYSTEM_WITHDRAW);
                pushInput.setDestUid(moneyEntity.getUserId());
                pushInput.setSystemInput(new SystemMessageInput(moneyEntity.getPhone(), loveCoupons, moneyEntity.getAccount()));
                pushInput.setBusinessId(moneyEntity.getId());
                Result result = sf.getMessagePushService().optionPush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, pushInput);
            }
            return new Result().setCode(updateById ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(updateById ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception("提现." + x.getLocalizedMessage());
        }
    }


    public Result findCount(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>(30);
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getBeginTime())) {
                if (StringUtils.isEmpty(withdrawMoneyEntity.getEndTime())) {
                    throw new Exception("截止日期参数异常.");
                }
            }
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getEndTime())) {
                if (StringUtils.isEmpty(withdrawMoneyEntity.getBeginTime())) {
                    throw new Exception("开始日期参数异常.");
                }

            }
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getEndTime()) && StringUtils.isNotEmpty(withdrawMoneyEntity.getBeginTime())
                    && withdrawMoneyEntity.getDay() > 0) {
                throw new Exception("条件查询参数异常.");
            }
            if (StringUtils.isEmpty(withdrawMoneyEntity.getEndTime()) && StringUtils.isEmpty(withdrawMoneyEntity.getBeginTime())
                    && withdrawMoneyEntity.getDay() == 0) {
                withdrawMoneyEntity.setDay(4);
            }
            IPage<Map<String, Object>> mapIPage = new Page<>(withdrawMoneyEntity.getPageSum(), withdrawMoneyEntity.getPageSize());
            String[] sqlDate = sqlDate(withdrawMoneyEntity.getDay(), withdrawMoneyEntity.getBeginTime() == null ? "" : withdrawMoneyEntity.getBeginTime(),
                    withdrawMoneyEntity.getEndTime() == null ? "" : withdrawMoneyEntity.getEndTime());
            IPage<Map<String, Object>> count = mf.getInvitationCodeMapper().findCount(mapIPage, sqlDate);
            return new Result(count);
        } catch (Exception x) {
            throw new Exception("统计." + x.getLocalizedMessage());
        }
    }

    public Result findBottomCount(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        try {
            Map<String, Object> map = new HashMap<>(30);
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getBeginTime())) {
                if (StringUtils.isEmpty(withdrawMoneyEntity.getEndTime())) {
                    throw new Exception("截止日期参数异常.");
                }
            }
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getEndTime())) {
                if (StringUtils.isEmpty(withdrawMoneyEntity.getBeginTime())) {
                    throw new Exception("开始日期参数异常.");
                }

            }
            if (StringUtils.isNotEmpty(withdrawMoneyEntity.getEndTime()) && StringUtils.isNotEmpty(withdrawMoneyEntity.getBeginTime())
                    && withdrawMoneyEntity.getDay() > 0) {
                throw new Exception("条件查询参数异常.");
            }
            if (StringUtils.isEmpty(withdrawMoneyEntity.getEndTime()) && StringUtils.isEmpty(withdrawMoneyEntity.getBeginTime())
                    && withdrawMoneyEntity.getDay() == 0) {
                withdrawMoneyEntity.setDay(4);
            }
            Map<String, Object> count = mf.getInvitationCodeMapper().findBottomCount(withdrawMoneyEntity);
            return new Result(count);
        } catch (Exception x) {
            throw new Exception("统计." + x.getLocalizedMessage());
        }
    }

    public String[] sqlDate(Integer day, String beginTime, String endTime) {
        String[] dates;
        switch (day) {
            case 1:
                dates = DateUtil.getDaysBetwwen(DateUtil.getDayOfMonth()).toArray(new String[DateUtil.getDaysBetwwen(DateUtil.getDayOfMonth()).size()]);
                break;
            case 2:
                dates = new String[]{DateUtil.getYesterdayByCalendar(-1)};
                break;
            case 3:
                dates = DateUtil.getDaysBetwwen(3).toArray(new String[DateUtil.getDaysBetwwen(3).size()]);
                break;
            case 4:
                dates = new String[]{DateUtil.getYesterdayByCalendar(0)};
                break;
            case 7:
                dates = DateUtil.getDaysBetwwen(7).toArray(new String[DateUtil.getDaysBetwwen(7).size()]);
                break;
            default:
                dates = DateUtil.getDays(beginTime, endTime).toArray(new String[DateUtil.getDays(beginTime, endTime).size()]);

        }
        return dates;
    }


}
