package com.ymw.love.system.service.seeDoctor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import com.ymw.love.system.entity.seeDoctor.input.RegistMangerInput;
import com.ymw.love.system.entity.seeDoctor.input.TreadWayInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

/**
 * 后台管理业务类
 */
@Service
public class RegistManagerService extends BaseService {

    /**
     * 待就诊接口查询
     * @param mangerInput
     * @return
     */
    public Result seeDoctorQuery(RegistMangerInput mangerInput, Integer optionStatus, AUserVO adminUser){
        Map<String, String> ruleMap = getQueryDate(mangerInput.getStartDate(), mangerInput.getEndDate(), mangerInput.getDateValue(), optionStatus);
        mangerInput.setStartDate(ruleMap.get("startDate"));
        mangerInput.setEndDate(ruleMap.get("endDate"));
        String hospital = adminUser.getDataTier()==2?adminUser.getHospitalId():"";
        if (StringUtils.isEmpty(mangerInput.getHospitalId()) && StringUtils.isNotEmpty(hospital)) {
            mangerInput.setHospitalId(adminUser.getHospitalId());
        }
        Page<?> page = new Page<>(mangerInput.getPageNum(), mangerInput.getPageSize());
        IPage<?> seeDoctorQueryResultIPage = null;
        //状态判断 就诊操作  1 待就诊 2 已就诊  3已取消 4.已失效 5号源管理
        if(optionStatus == Constant.PRE_SEE_DOCTOR){
            //1 待就诊
            seeDoctorQueryResultIPage = mf.getRegistManagerMapper().selectPreList(page, mangerInput);
        }else if(optionStatus == Constant.FINISH_SEE_DOCTOR){
            //2 已就诊
            seeDoctorQueryResultIPage = mf.getRegistManagerMapper().selectFinishList(page, mangerInput);
        }else if(optionStatus == Constant.CANCEL_SEE_DOCTOR){
            //3.已取消
            seeDoctorQueryResultIPage = mf.getRegistManagerMapper().selectCancelDateList(page, mangerInput);
        }else if(optionStatus == Constant.EXPIRED_SEE_DOCTOR){
            //4.已失效
            seeDoctorQueryResultIPage = mf.getRegistManagerMapper().selectOutOfDateList(page, mangerInput);
        }else if(optionStatus == Constant.NUMBER_MANAGER){
            //5.号源管理
            seeDoctorQueryResultIPage = mf.getRegistManagerMapper().selectNumbersList(page, mangerInput);
        }
        return new Result(seeDoctorQueryResultIPage);
    }

    public Map<String, String> getQueryDate(String startDate, String endDate, Integer dateValue, Integer optionStatus){
        Map<String, String> ruleMap = null;
        //优先级，先按固定值查询
        if(dateValue != null){
            ruleMap = getDateMapByOptionStatus(optionStatus, dateValue);
        }else{
            ruleMap = CommonUtil.getDateQueryMethod(startDate, endDate);
        }
        String start = ruleMap.get("startDate");
        String end = ruleMap.get("endDate");
        if(optionStatus == Constant.PRE_SEE_DOCTOR || optionStatus == Constant.FINISH_SEE_DOCTOR ||
                optionStatus == Constant.EXPIRED_SEE_DOCTOR || optionStatus == Constant.NUMBER_MANAGER){
            if(StringUtils.isNotEmpty(start)){
                Date startTmp = DateUtil.parseStrToDate(start, DateUtil.DATE_FORMAT_YYYY_MM_DD);
                ruleMap.put("startDate", DateUtil.parseDateToStr(startTmp,DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
            if(StringUtils.isNotEmpty(end)){
                Date endTmp = DateUtil.parseStrToDate(end, DateUtil.DATE_FORMAT_YYYY_MM_DD);
                ruleMap.put("endDate", DateUtil.parseDateToStr(endTmp,DateUtil.DATE_FORMAT_YYYY_MM_DD));
            }
        }
        return ruleMap;
    }

    /**
     * 根据优先级获取搜索条件（开始时间/结束时间）
     * @param optionStatus
     * @param dateValue
     * @return
     */
    public Map<String, String> getDateMapByOptionStatus(Integer optionStatus, Integer dateValue){
        Map<String, String> dateMap = null;
        if(dateValue<1 || dateValue > 5){
            throw new MissRequiredParamException(Constant.QUERY_PARAM_INVALID);
        }
        if(optionStatus == 1 || optionStatus == 5){
            //待就诊  1 今天  2 明天 3 未来3天 4 未来7天 5 未来1个月
            dateMap = CommonUtil.getPreEndDateByDateValue(dateValue);
        }else if(optionStatus == 2 || optionStatus == 3 || optionStatus == 4){
            //已就诊/已过期/已取消 1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月
            dateMap = CommonUtil.getFinishEndDateByDateValue(dateValue);
        }
        return dateMap;
    }



    public Result treatOption(AUserVO au, TreadWayInput input){
        String orderId = input.getOrderId();
        Integer treatWay = input.getTreatWay();
        String uid = au.getId();
        if(StringUtils.isEmpty(orderId)){
            throw new MissRequiredParamException(Constant.ORDER_NOT_EXIST);
        }
        if(treatWay == null || treatWay < 1 || treatWay > 2){
            throw new MissRequiredParamException(Constant.OPERATION_TREAT_WAY_PARAM_INVALID);
        }
        //就诊时间判断
        USeeDoctor seeDoctor = sf.getAppointmentService().getRecordByOrderId(orderId);
        Long distantDays = DateUtil.getDistance(seeDoctor.getTreatmentDate(), DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        if(distantDays != 0){
            throw new MissRequiredParamException(Constant.TREAT_DATE_INVALID);
        }
        USeeDoctor uSeeDoctor = new USeeDoctor();
        uSeeDoctor.setActUid(uid);
        uSeeDoctor.setTreatWay(treatWay);
        uSeeDoctor.setOptionStatus(Constant.FINISH_SEE_DOCTOR);
        mf.getSeeDoctorMapper().update(uSeeDoctor, new QueryWrapper<USeeDoctor>().eq("order_id", orderId));
        return new Result();
    }
}
