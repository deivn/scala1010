package com.ymw.love.system.controller.web.coupon;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.coupon.CouponsEntity;
import com.ymw.love.system.entity.coupon.UserExtendEntity;
import com.ymw.love.system.entity.coupon.WithdrawMoneyEntity;
import com.ymw.love.system.entity.coupon.WithdrawRecordEntity;
import com.ymw.love.system.service.BaseService;
import org.springframework.web.bind.annotation.*;

/**
 * @author zjc
 * @version 1.1
 * invitation code
 * @date 2019年8月26日14:15:34
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/web")
public class InvitationCodeController extends BaseService {



    @Authority(Resource.enter.web_user)
    @GetMapping("/get/code")
    public Result getInviteCode() throws Exception {
        return sf.getCodeService().getInviteCode();
    }

    @Authority(Resource.enter.web_user)
    @GetMapping("/coupons/List")
    public Result findCouponsList(CouponsEntity couponsEntity) throws Exception {
        return sf.getCodeService().findCouponsList(couponsEntity);
    }

    @Authority(Resource.enter.web_user)
    @GetMapping("/coupons/type")
    public Result findCouponsTypeList(CouponsEntity couponsEntity) throws Exception {
        return sf.getCodeService().findCouponsTypeList(couponsEntity);
    }

    @Authority(Resource.enter.web_user)
    @GetMapping("/love/coupons")
    public Result findLoveCouponsList(CouponsEntity couponsEntity) throws Exception {
        return sf.getCodeService().findLoveCouponsList(couponsEntity);
    }


    @Authority(Resource.enter.web_user)
    @GetMapping("/withdraw/money")
    public Result findUserWithdrawMoney(UserExtendEntity userExtendEntity) throws Exception {
        return sf.getCodeService().findUserWithdrawMoney(userExtendEntity);
    }

    @Authority(Resource.enter.web_user)
    @PostMapping ("/ins/withdraw")
    public Result insUserWithdraw(@RequestBody WithdrawMoneyEntity moneyEntity) throws Exception {
        return sf.getCodeService().insUserWithdraw(moneyEntity);
    }

    @Authority(Resource.enter.web_user)
    @GetMapping("/withdraw/record/List")
    public Result findWithdrawList(WithdrawRecordEntity withdrawRecordEntity) throws Exception {
        return sf.getCodeService().findWebWithdrawList(withdrawRecordEntity);
    }







}
