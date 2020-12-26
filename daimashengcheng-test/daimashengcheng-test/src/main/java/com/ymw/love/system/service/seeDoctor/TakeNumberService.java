package com.ymw.love.system.service.seeDoctor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.seeDoctor.UTakeNumber;
import com.ymw.love.system.entity.seeDoctor.input.TakeNumberInput;
import com.ymw.love.system.entity.seeDoctor.input.UpdateNumbersInput;
import com.ymw.love.system.entity.seeDoctor.query.TakeNumberQueryResult;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TakeNumberService extends BaseService {

    @Transactional
    public Result numberDistribute(TakeNumberInput input){
        String hospitalId = input.getHospitalId();
        String startDate = input.getStartDate();
        String endDate = input.getEndDate();
        Integer amNumbers = input.getAmNumbers();
        Integer pmNumbers = input.getPmNumbers();
        paramVerify(hospitalId, startDate, endDate, amNumbers, pmNumbers);
        List<String> days = DateUtil.getDays(startDate, endDate);
        Map<String, Object> dateMap = checkRepeatDate(hospitalId, days);
        List<String> repeatDates = (List)dateMap.get("repeatDates");
        List<String> avaliableDates = (List)dateMap.get("avaliableDates");
        List<UTakeNumber> takeNumbers = buildNumberDetails(avaliableDates, input);
        batchAddNumbers(takeNumbers);
        if(repeatDates != null && repeatDates.size() > 0){
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < repeatDates.size(); i++){
                sb.append(repeatDates.get(i)).append(" ");
            }
            return new Result().setCode(SystemEnum.FAIL).setMsg(Constant.NUMBER_REPEAT_DATE+":"+sb.toString());
        }
        return new Result();
    }

    public List<UTakeNumber> buildNumberDetails(List<String> dates, TakeNumberInput input){
        List<UTakeNumber> takeNumbers = new ArrayList<UTakeNumber>();
        String hospitalId = input.getHospitalId();
        Integer amNumbers = input.getAmNumbers();
        Integer pmNumbers = input.getPmNumbers();
        for(String date: dates){
            UTakeNumber takeNumber = new UTakeNumber(hospitalId, date,amNumbers, pmNumbers);
            takeNumbers.add(takeNumber);
        }
        return takeNumbers;
    }

    public Map<String, Object> checkRepeatDate(String hospitalId, List<String> dates){
        Map<String, Object> dateMap = new HashMap<String, Object>();
        List<UTakeNumber> takeNumbers = mf.getTakeNumberMapper().selectList(new QueryWrapper<UTakeNumber>().eq("hospital_id", hospitalId));
        Set<String> dateSet = new HashSet<String>();
        List<String> repeatDates = new ArrayList<String>();
        if(takeNumbers != null && takeNumbers.size() > 0){
            for(int i = 0; i< takeNumbers.size(); i++){
                dateSet.add(takeNumbers.get(i).getCreateTime());
            }
        }
        if(dateSet.size() > 0){
            Iterator<String> iter = dateSet.iterator();
            while(iter.hasNext()){
                String date = iter.next();
                for(int i = 0; i< dates.size(); i++){
                    if(date.equals(dates.get(i))){
                        repeatDates.add(dates.get(i));
                        dates.remove(i);
                    }
                }
            }
        }
        dateMap.put("repeatDates", repeatDates);
        dateMap.put("avaliableDates", dates);
        return dateMap;
    }

    public void paramVerify(String hospitalId, String startDate, String endDate, Integer amNumbers, Integer pmNumbers){
        if(StringUtils.isEmpty(hospitalId)){
            throw new MissRequiredParamException(Constant.HOSPITAL_ID_EMPTY);
        }
        if(StringUtils.isEmpty(startDate)){
            throw new MissRequiredParamException(Constant.STARTDATE_EMPTY);
        }
        if(StringUtils.isEmpty(endDate)){
            throw new MissRequiredParamException(Constant.ENDDATE_EMPTY);
        }
        //起始时间不能晚于当天
        Long distance = DateUtil.getDistance(startDate, DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        if(distance < 1){
            throw new MissRequiredParamException(Constant.STARTDATE_INVALID);
        }
        //结束时间不能小于开始时间
        Long dateDistance = DateUtil.getDistance(endDate, startDate);
        if(dateDistance < 0){
            throw new MissRequiredParamException(Constant.ENDDATE_INVALID);
        }
        if(amNumbers == null || pmNumbers == null){
            throw new MissRequiredParamException(Constant.AM_PM_NUMBERS_EMPTY);
        }
    }


    @Transactional
    public void batchAddNumbers(List<UTakeNumber> takeNumbers){
        for(UTakeNumber takeNumber: takeNumbers){
            mf.getTakeNumberMapper().insert(takeNumber);
        }
    }

    public Result updateNumbers(UpdateNumbersInput input){
        Integer amNumbers = input.getAmNumbers();
        Integer pmNumbers = input.getPmNumbers();
        String id = input.getId();
        String appointDate = input.getAppointDate();
        if(StringUtils.isEmpty(id)){
            throw new MissRequiredParamException(Constant.NUMBER_ID_EMPTY);
        }
        if(StringUtils.isEmpty(appointDate)){
            throw new MissRequiredParamException(Constant.APPOINT_DATE_EMPTY);
        }
        if(amNumbers == null || pmNumbers == null){
            throw new MissRequiredParamException(Constant.AM_PM_NUMBER_EMPTY);
        }
//        Long distance = DateUtil.getDistance(appointDate, DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
//        if(distance < 0){
//            throw new MissRequiredParamException(Constant.NUMBER_EDIT_ERROR);
//        }
        UTakeNumber number = getNumbers(id);
        if(!appointDate.equals(number.getCreateTime())){
            throw new MissRequiredParamException(Constant.NUMBER_EDIT_DATE_INVALID);
        }
        if(amNumbers != null){
            if(amNumbers < number.getAmSource()){
               //只能改大不能改小
                throw new MissRequiredParamException(Constant.AM_NUMBER_NOTALLOWED_LT);
            }
            if(pmNumbers == null){
                number.setTotalSource(amNumbers + number.getPmSource());
            }else{
                if(pmNumbers < number.getPmSource()) {
                    //只能改大不能改小
                    throw new MissRequiredParamException(Constant.PM_NUMBER_NOTALLOWED_LT);
                }
                number.setPmSource(pmNumbers);
                number.setPmSourceUnuse(pmNumbers);
                number.setTotalSource(amNumbers + pmNumbers);
            }
            number.setAmSource(amNumbers);
            number.setAmSourceUnuse(amNumbers);
        }else{
            if(pmNumbers < number.getPmSource()) {
                //只能改大不能改小
                throw new MissRequiredParamException(Constant.PM_NUMBER_NOTALLOWED_LT);
            }
            number.setPmSource(pmNumbers);
            number.setPmSourceUnuse(pmNumbers);
            number.setTotalSource(pmNumbers + number.getAmSource());
        }
        mf.getTakeNumberMapper().update(number, new QueryWrapper<UTakeNumber>().eq("id", id));
        return new Result();
    }

    public Result selectNumbersByHospitalId(String hospitalId){
        if(StringUtils.isEmpty(hospitalId)){
            throw new MissRequiredParamException(Constant.HOSPITAL_ID_EMPTY);
        }
        //展示7天的记录
        Page<TakeNumberQueryResult> page = new Page<>(1, 7);
        IPage<TakeNumberQueryResult> pageRecord = mf.getNumberQueryResultMapper().findNumbersByHospitalId(page, hospitalId);
        return new Result(pageRecord);
    }

    public UTakeNumber getNumbers(String id){
        List<UTakeNumber> numbers = mf.getTakeNumberMapper().selectList(new QueryWrapper<UTakeNumber>().eq("id", id));
        if(numbers == null || numbers.size() == 0){
            throw new MissRequiredParamException(Constant.NUMBER_ORIGIN_EMPTY);
        }
        return numbers.get(0);
    }
}
