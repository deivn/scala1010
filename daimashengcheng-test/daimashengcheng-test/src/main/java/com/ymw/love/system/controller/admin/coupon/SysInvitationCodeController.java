package com.ymw.love.system.controller.admin.coupon;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.coupon.CouponsEntity;
import com.ymw.love.system.entity.coupon.UserExtendEntity;
import com.ymw.love.system.entity.coupon.WithdrawMoneyEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.coupon.InvitationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zjc
 * @version 1.1
 * invitation code
 * @date 2019年8月26日14:15:34
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/admin")
public class SysInvitationCodeController extends BaseService {



    @Authority(Resource.enter.admin_user)
    @GetMapping("/hx/list")
    public Result findHxList(CouponsEntity couponsEntity) throws Exception {
        return sf.getCodeService().findHxList(couponsEntity);
    }

    @Authority(value = Resource.enter.admin_user,content = "优惠券核销")
    @PostMapping("/sys/hx")
    public Result updSysHx(@RequestBody CouponsEntity couponsEntity) throws Exception {
        return sf.getCodeService().updSysHx(couponsEntity);
    }

    @Authority(Resource.enter.admin_user)
    @GetMapping("/withdraw/list")
    public Result findWithdrawList(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        return sf.getCodeService().findWithdrawList(withdrawMoneyEntity);
    }

    @Authority(value = Resource.enter.admin_user,content = "提现")
    @PostMapping("/withdraw")
    public Result updWithdraw(@RequestBody WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        return sf.getCodeService().updWithdraw(withdrawMoneyEntity);
    }

    @Authority(value = Resource.enter.admin_user)
    @GetMapping("/count")
    public Result findCount(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        return sf.getCodeService().findCount(withdrawMoneyEntity);
    }

    @Authority(value = Resource.enter.admin_user,holdUp=false)
    @GetMapping("/bottom/count")
    public Result findBottomCount(WithdrawMoneyEntity withdrawMoneyEntity) throws Exception {
        return sf.getCodeService().findBottomCount(withdrawMoneyEntity);
    }



}
