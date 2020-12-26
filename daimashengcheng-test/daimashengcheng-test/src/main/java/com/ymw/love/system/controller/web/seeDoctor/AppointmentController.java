package com.ymw.love.system.controller.web.seeDoctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.seeDoctor.input.AppointmentInput;
import com.ymw.love.system.entity.seeDoctor.input.DetailOptionInput;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.HospitalService;
import com.ymw.love.system.service.seeDoctor.AppointmentService;
import com.ymw.love.system.service.seeDoctor.TakeNumberService;
import com.ymw.love.system.vo.UUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/web/seeDoctor")
@ResponseBody
@Api(tags = { "预约挂号" })
public class AppointmentController extends BaseService  {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TakeNumberService numberService;

    @Autowired
    private UserLogInInfo userLogInInfo;
    
    @Autowired
    private HospitalService HospitalService;

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "预约挂号医院、个人信息页面渲染", notes = "")
    @GetMapping("/appointment/queryInfo")
    public Result preAppointment(){
        UUserVO webUser = userLogInInfo.getWebUser();
        return appointmentService.appointmentQuery(webUser);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "预约挂号提交", notes = "")
    @PostMapping("/appointment/option")
    public Result makeAppointment(@RequestBody AppointmentInput input){
        UUserVO webUser = userLogInInfo.getWebUser();
        log.info("------预约挂号提交(appointment/option)----------------"+ JSON.toJSONString(input));
        return appointmentService.makeAppointment(input, webUser);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "个人挂号记录查询", notes = "")
    @PostMapping("/appointment/find/records")
    public Result findRecordsByUid(@RequestBody BasicDTO basicDTO){
        UUserVO webUser = userLogInInfo.getWebUser();
        return appointmentService.findRecords(webUser, basicDTO);
    }



    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "挂号记录列表中的操作(取消预约2，去评价4)", notes = "")
    @PostMapping("/appointment/detailOption")
    public Result detailOption(@RequestBody DetailOptionInput optionInput){
        UUserVO webUser = userLogInInfo.getWebUser();
        log.info("------挂号记录列表中的操作(取消预约2，去评价4)appointment/detailOption----------------"+ JSON.toJSONString(optionInput));
        return appointmentService.optionChoose(optionInput, webUser);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "医院评价列表查询", notes = "")
    @GetMapping("/appointment/hospitalEvaluationQuery")
    public Result hospitalEvaluationQuery(@RequestParam("hospitalId")String hospitalId){
        return appointmentService.hospitalEvaluationQuery(hospitalId);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "根据医院取号列表查询最近七天", notes = "")
    @GetMapping("/find/latestSevenDaysRecord")
    public Result selectNumbersByHospitalId(@RequestParam("hospitalId") String hospitalId) {
        return numberService.selectNumbersByHospitalId(hospitalId);
    }

    @ApiOperation(value = "获取医院列表", notes = "分页，经纬度 ")
    @PostMapping("/find/hospital/list")
    public Result findHospitalList(@RequestBody BasicDTO  arg) {
    	return new Result(HospitalService.findRecommend(arg)).setRemove(new String[] {"state","createsTime"});
    }
    
    
}
