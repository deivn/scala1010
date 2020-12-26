package com.ymw.love.system.controller.admin.registManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.entity.seeDoctor.input.RegistMangerInput;
import com.ymw.love.system.entity.seeDoctor.input.TreadWayInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.AUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin/registmanager")
@ResponseBody
@Api(tags = { "后台挂号管理" })
public class RegistMangerController extends BaseService  {

    @Autowired
    private UserLogInInfo userLogInInfo;
    

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "待就诊接口查询", notes = "")
    @PostMapping("/query/pre/seedoctor/list")
    public Result preSeeDoctor(@RequestBody RegistMangerInput mangerInput) {
        AUserVO adminUser = userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().seeDoctorQuery(mangerInput, Constant.PRE_SEE_DOCTOR, adminUser);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "已就诊接口查询", notes = "")
    @PostMapping("/query/finish/seedoctor/list")
    public Result finishSeeDoctor(@RequestBody RegistMangerInput mangerInput) {
        AUserVO adminUser = userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().seeDoctorQuery(mangerInput, Constant.FINISH_SEE_DOCTOR, adminUser);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "已取消接口查询", notes = "")
    @PostMapping("/query/cancel/seedoctor/list")
    public Result cancelSeeDoctor(@RequestBody RegistMangerInput mangerInput) {
        AUserVO adminUser = userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().seeDoctorQuery(mangerInput, Constant.CANCEL_SEE_DOCTOR, adminUser);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "已过期接口查询", notes = "")
    @PostMapping("/query/outdate/seedoctor/list")
    public Result outDateSeeDoctor(@RequestBody RegistMangerInput mangerInput) {
        AUserVO adminUser = userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().seeDoctorQuery(mangerInput, Constant.EXPIRED_SEE_DOCTOR, adminUser);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "号源管理接口查询", notes = "")
    @PostMapping("/query/takenumber/list")
    public Result queryNumbers(@RequestBody RegistMangerInput mangerInput) {
        AUserVO adminUser = userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().seeDoctorQuery(mangerInput, Constant.NUMBER_MANAGER, adminUser);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "就诊方式(线上/下)操作选择", notes = "")
    @PostMapping("/update/treatway")
    public Result treatWayChoose(@RequestBody TreadWayInput input) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getRegistManagerService().treatOption(au, input);
    }

}
