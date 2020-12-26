package com.ymw.love.system.service.seeDoctor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.UHospital;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.entity.message.input.push.MessagePushInput;
import com.ymw.love.system.entity.message.input.push.SystemMessageInput;
import com.ymw.love.system.entity.seeDoctor.UPatientEvaluation;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import com.ymw.love.system.entity.seeDoctor.UTakeNumber;
import com.ymw.love.system.entity.seeDoctor.input.AppointmentInput;
import com.ymw.love.system.entity.seeDoctor.input.DetailOptionInput;
import com.ymw.love.system.entity.seeDoctor.input.EvaluationOption;
import com.ymw.love.system.entity.seeDoctor.query.EvaluationQueryResult;
import com.ymw.love.system.entity.seeDoctor.query.SeeDoctorQueryResult;
import com.ymw.love.system.entity.seeDoctor.query.TakeNumberQueryResult;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mq.sender.MessageSender;
import com.ymw.love.system.mq.sender.SeeDoctorSender;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.SnowFlakeUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.UUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class AppointmentService extends BaseService {


    @Autowired
    private SeeDoctorSender seeDoctorSender;

    @Autowired
    private MessageSender messageSender;


    public Result appointmentQuery(UUserVO webUser){
        
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("userInfo", webUser);
        return new Result().setData(resMap);
    }

    /**
     * 预约挂号逻辑
     * @param input
     * @return
     */
    @Transactional
    public Result makeAppointment(AppointmentInput input, UUserVO webUser){
        String treatDate = input.getTreatmentDate();
        long days = DateUtil.getDistance(treatDate, DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        if(days <= 0){
            throw new MissRequiredParamException(Constant.APPOINT_DATE_INVALID);
        }
        Integer s = mf.getSeeDoctorMapper().selectWeekday(input.getTreatmentDate());
        if(s != input.getWeekday()){
            throw new MissRequiredParamException(Constant.WEEKDAY_ERROR);
        }
        String hospitalId = input.getHospitalId();
        verifyParam(hospitalId, treatDate, webUser.getId());
        //当天不能预约
        Integer dayHalfStatus = input.getDayHalfStatus();
//        Integer weekday = input.getWeekday();
        //预约号源数减一, 不能变为负数
        UTakeNumber takeNumber = getNumbers(hospitalId, treatDate);
        if(dayHalfStatus == Constant.AM){
            if(takeNumber.getAmSourceUsed() == takeNumber.getAmSource()){
                throw new MissRequiredParamException(Constant.AM_NUMBER_USE_UP);
            }
            takeNumber.setAmSourceUsed(takeNumber.getAmSourceUsed()+1);
            takeNumber.setAmSourceUnuse(takeNumber.getAmSource() - takeNumber.getAmSourceUsed());
            takeNumber.setPmSource(null);
            takeNumber.setPmSourceUnuse(null);
            takeNumber.setPmSourceUsed(null);
        }else if(dayHalfStatus == Constant.PM){
            if(takeNumber.getPmSourceUsed() == takeNumber.getPmSource()){
                throw new MissRequiredParamException(Constant.PM_NUMBER_USE_UP);
            }
            takeNumber.setPmSourceUsed(takeNumber.getPmSourceUsed() + 1);
            takeNumber.setPmSourceUnuse(takeNumber.getPmSource() - takeNumber.getPmSourceUsed());
            takeNumber.setAmSource(null);
            takeNumber.setAmSourceUnuse(null);
            takeNumber.setAmSourceUsed(null);
        }else{
            throw new MissRequiredParamException(Constant.DAY_HALF_STATUS_ERROR);
        }
        String code = SnowFlakeUtil.getRandom(10) + "";
        USeeDoctor seeDoctor = new USeeDoctor(input, webUser, code);
        mf.getSeeDoctorMapper().insert(seeDoctor);
        seeDoctorSender.subTractNumber(takeNumber);
        seeDoctorSender.smsSend(seeDoctor);
        //系统消息推送，预约成功
        messageSender.messagePush(buildMessagePush(seeDoctor, Constant.MESSAGE_SYSTEM_APPOINT_SUCCESS));
        return new Result();
    }

    public String getDateDetailByTreadDate(String treadDate, Integer dayHalfStatus){
        String date = getDate(treadDate);
        String language = dayHalfStatus == null || dayHalfStatus == 0 ? "": (dayHalfStatus == Constant.AM?"上午":"下午");
        return date + language;
    }

    public String getDate(String treadDate){
        String[] dates = treadDate.split("-");
        String date = dates[0]+"年"+dates[1]+"月"+dates[2]+"日";
        return date;
    }

    public String getDayOfWeek(Integer weekday){
        String dayOfWeek = "";
        switch (weekday){
            case 1:
                dayOfWeek = "星期一";
                break;
            case 2:
                dayOfWeek = "星期二";
                break;
            case 3:
                dayOfWeek = "星期三";
                break;
            case 4:
                dayOfWeek = "星期四";
                break;
            case 5:
                dayOfWeek = "星期五";
                break;
            case 6:
                dayOfWeek = "星期六";
                break;
            case 7:
                dayOfWeek = "星期日";
                break;
        }
        return dayOfWeek;
    }

    public String puseMessageKeyInfo(USeeDoctor seeDoctor){
        StringBuffer sb = new StringBuffer();
        //2.获取title
        UHospital hospitalRecord = sf.getHospitalService().getHospitalRecord(seeDoctor.getHospitalId());
        String title = hospitalRecord.getTitle();
        //日期
        String treatDate = getDate(seeDoctor.getTreatmentDate());
        //星期几
        String weekday = getDayOfWeek(seeDoctor.getWeekday());
        String dayHalfExpress = seeDoctor.getDayHalfStatus() == null || seeDoctor.getDayHalfStatus() == 0 ? "": (seeDoctor.getDayHalfStatus() == Constant.AM?"上午8：00-12：00":"下午12：30-17：30");
        sb.append(title).append(treatDate).append("(").append(weekday).append(")").append(dayHalfExpress);
        return sb.toString();
    }

    public UMessagePush buildMessagePush(USeeDoctor seeDoctor, Integer appointStatus){
        MessagePushInput pushInput = new MessagePushInput();
        SystemMessageInput systemInput = new SystemMessageInput(puseMessageKeyInfo(seeDoctor));
        pushInput.setSystemInput(systemInput);
        pushInput.setOption(appointStatus);
        Map<String, String> body = sf.getMessagePushService().buildMessageBody(pushInput);
        UMessagePush messagePush  = new UMessagePush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, appointStatus,
                seeDoctor.getOrderId(), seeDoctor.getUserId(), body.get("title"), body.get("content"));
        return messagePush;
    }

    public void verifyParam(String hospitalId, String treatDate, String userId){
        if(StringUtils.isEmpty(hospitalId)){
            throw new MissRequiredParamException(Constant.HOSPITAL_ID_EMPTY);
        }
        if(StringUtils.isEmpty(treatDate)){
            throw new MissRequiredParamException(Constant.APPOINT_DATE_EMPTY);
        }
        //一个人不能在某个日期重复预约
        List<USeeDoctor> records = mf.getSeeDoctorMapper().selectList(new QueryWrapper<USeeDoctor>().eq("user_id", userId).eq("treatment_date", treatDate).eq("hospital_id", hospitalId));
        if(records.size() > 0){
            throw new MissRequiredParamException(Constant.REPEAT_APPOINT_ON_ONDATE);
        }
    }

    public Result findRecords(UUserVO webUser, BasicDTO basicDTO){
        Integer type = basicDTO.getType();
        Page<SeeDoctorQueryResult> page = new Page<>(basicDTO.getPageNum(), basicDTO.getPageSize());
        IPage<SeeDoctorQueryResult> pageRecord = null;
        if(type == null){
            //查询所有
            pageRecord = mf.getSeeDoctorQueryResultMapper().findRecordsByUid(page, webUser.getId());
        }else{
            //分类查询1.待就诊  2.已就诊 3.已取消  4.已失效
            if(type < Constant.PRE_SEE_DOCTOR || type > Constant.EXPIRED_SEE_DOCTOR){
                throw new MissRequiredParamException(Constant.SEEDOCTOR_TYPE_INVALID);
            }
            pageRecord = mf.getSeeDoctorQueryResultMapper().findRecordsByType(page, webUser.getId(), type);
        }
        return new Result(pageRecord);
    }


    /**
     * 规则 取消预约只有能就诊日期的前一天或更早
     * 2.取消预约  4.去评价
     * @param optionInput
     * @return
     */
    @Transactional
    public Result optionChoose(DetailOptionInput optionInput, UUserVO webUser){
        String orderId = optionInput.getOrderId();
        if(StringUtils.isEmpty(orderId)){
            throw new MissRequiredParamException(Constant.ORDER_NOT_EXIST);
        }
        USeeDoctor record = getRecordByOrderId(orderId);
        if(record.getOptionStatus() == Constant.EXPIRED_SEE_DOCTOR){
            throw new MissRequiredParamException(Constant.ORDER_EXPLAIN);
        }
        paramVerifyByOption(optionInput.getOption(), record, optionInput.getEvaluationOption());
        String option = optionInput.getOption();
        if(option.equals(Constant.CANCEL_RESEVATION)){
            //2.取消预约
            Long distantDays = DateUtil.getDistance(record.getTreatmentDate(), DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
            if(distantDays < 1){
                throw new MissRequiredParamException(Constant.CANCEL_DATE_INVALID);
            }
            record.setOptionStatus(Constant.CANCEL_SEE_DOCTOR);
            record.setCancelTime(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            //预约号源数加一
            UTakeNumber takeNumber = getNumbers(record.getHospitalId(), record.getTreatmentDate());
            Integer dayHalfStatus = record.getDayHalfStatus();
            if(!record.getTreatmentDate().equals(takeNumber.getCreateTime())){
                throw new MissRequiredParamException(Constant.TREATE_DATE_DISMITCH);
            }
            if(dayHalfStatus == Constant.AM){
                takeNumber.setAmSourceUnuse(takeNumber.getAmSourceUnuse() + 1);
                takeNumber.setAmSourceUsed(takeNumber.getAmSourceUsed() - 1);
                takeNumber.setPmSource(null);
                takeNumber.setPmSourceUnuse(null);
                takeNumber.setPmSourceUsed(null);
            }else if(dayHalfStatus == Constant.PM){
                takeNumber.setPmSourceUnuse(takeNumber.getPmSourceUnuse() + 1);
                takeNumber.setPmSourceUsed(takeNumber.getPmSourceUsed() - 1);
                takeNumber.setAmSource(null);
                takeNumber.setAmSourceUnuse(null);
                takeNumber.setAmSourceUsed(null);
            }
            seeDoctorSender.addNumber(takeNumber);
            //系统消息推送，取消预约
            messageSender.messagePush(buildMessagePush(record, Constant.MESSAGE_SYSTEM_APPOINT_CANCEL));
        }else if(option.equals(Constant.TO_EVALUATE)){
            //4.去评价
            UPatientEvaluation uPatientEvaluation = new UPatientEvaluation(optionInput.getEvaluationOption(), webUser, record.getHospitalId(), record.getTreatmentDate());
            mf.getPatientEvaluationMapper().insert(uPatientEvaluation);
            record.setOptionStatus(Constant.FINISH_SEE_DOCTOR);
        }
        mf.getSeeDoctorMapper().update(record, new QueryWrapper<USeeDoctor>().eq("order_id", orderId));
        return new Result();
    }

    public Result hospitalEvaluationQuery(String hospitalId){
        if(StringUtils.isEmpty(hospitalId)){
            throw new MissRequiredParamException(Constant.HOSPITAL_ID_EMPTY);
        }
        //展示最新10条记录
        Page<TakeNumberQueryResult> page = new Page<>(1, 10);
        IPage<EvaluationQueryResult> evaluationRecords = mf.getPatientEvaluationMapper().selectPatientEvaluations(page, hospitalId);
        List<EvaluationQueryResult> evaluationQueryResults = nameEncpytionList(evaluationRecords.getRecords());
        evaluationRecords.setRecords(evaluationQueryResults);
        return new Result(evaluationRecords);
    }

    public List<EvaluationQueryResult> nameEncpytionList(List<EvaluationQueryResult> records){
        List<EvaluationQueryResult> evaluations = new ArrayList<EvaluationQueryResult>();
        if(records != null && records.size() > 0){
            for(int i = 0; i< records.size(); i++){
                EvaluationQueryResult queryResult = records.get(i);
                if(StringUtils.isNotEmpty(queryResult.getUserName())){
                    queryResult.setUserName(CommonUtil.nameEncryption(queryResult.getUserName(), 1));
                    evaluations.add(queryResult);
                }

            }
        }
        return evaluations;
    }


    public void paramVerifyByOption(String option, USeeDoctor record, EvaluationOption evaluationOption){
        String appointDate = record.getTreatmentDate();
        //1 待就诊 2 已就诊  3已取消 4.已失效
        Integer optionStatus = record.getOptionStatus();
        if(StringUtils.isEmpty(appointDate)){
            throw new MissRequiredParamException(Constant.APPOINT_DATE_EMPTY);
        }
        if(option.equals(Constant.TO_EVALUATE)){
            if(optionStatus != Constant.FINISH_SEE_DOCTOR){
                throw new MissRequiredParamException(Constant.EVALUATE_METHOD_INVALID);
            }
            if(evaluationOption == null){
                throw new MissRequiredParamException(Constant.EVALUATE_IS_EMPTY);
            }
            Integer evaluationGrade = evaluationOption.getEvaluationGrade();
            String evaluationLabel = evaluationOption.getEvaluationLabel();
            if(StringUtils.isEmpty(evaluationGrade) || evaluationGrade == 0){
                throw new MissRequiredParamException(Constant.EVALUATE_GRADE);
            }
            if(StringUtils.isEmpty(evaluationLabel)){
                throw new MissRequiredParamException(Constant.SERVICE_EVALUATION_CHOOSE_NONE);
            }
        } else if (option.equals(Constant.CANCEL_RESEVATION)) {
            if(optionStatus != Constant.PRE_SEE_DOCTOR){
                throw new MissRequiredParamException(Constant.CANCEL_METHOD_INVALID);
            }
        } else {
            throw new MissRequiredParamException(Constant.OPTION_INVALID);
        }
    }
    
    public UTakeNumber getNumbers(String hospitalId, String createTime){
        List<UTakeNumber> numbers = mf.getTakeNumberMapper().selectList(new QueryWrapper<UTakeNumber>().eq("hospital_id", hospitalId)
                .eq("create_time", createTime));
        if(numbers == null || numbers.size() == 0){
            throw new MissRequiredParamException(Constant.NUMBER_ORIGIN_EMPTY);
        }
        return numbers.get(0);
    }

    public USeeDoctor getRecordByOrderId(String orderId){
        List<USeeDoctor> records = mf.getSeeDoctorMapper().selectList(new QueryWrapper<USeeDoctor>().eq("order_id", orderId));
        if(records == null || records.size() == 0){
            throw new MissRequiredParamException(Constant.SEE_DOCTOR_EMTPTY);
        }
        return records.get(0);
    }
}


