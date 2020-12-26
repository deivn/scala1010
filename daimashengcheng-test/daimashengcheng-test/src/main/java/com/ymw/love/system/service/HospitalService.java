package com.ymw.love.system.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ymw.love.system.entity.message.UMessageCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.UHospital;
import com.ymw.love.system.entity.seeDoctor.UPatientEvaluation;
import com.ymw.love.system.entity.seeDoctor.input.RegistMangerInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.mapper.UHospitalMapper;
import com.ymw.love.system.mapper.seeDoctor.UPatientEvaluationMapper;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;
import com.ymw.love.system.vo.EvaluationQueryResultVO;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月15日 类说明： 医院
 */
@Service
public class HospitalService extends BaseService{


	
	

	public IPage<UHospital> findAll(BaseEntity be,String hospitalId) {
		Page<UHospital> page = new Page<>(be.getPageNum(), be.getPageSize());
		QueryWrapper qw=	new QueryWrapper<UHospital>();
		if(StringUtils.isNotEmpty(hospitalId)) {
			qw.eq("id", hospitalId);
		}
		    qw.eq("state", 1);
		    
		return mf.getUHospitalMapper().selectPage(page, qw);
	}
	
	public List<UHospital> findAllId(String id){
		QueryWrapper qw=	new QueryWrapper<UHospital>();
		if(StringUtils.isNotEmpty(id)) {
			qw.eq("id", id);
		}
		    qw.eq("state", 1);
		return  mf.getUHospitalMapper().selectList(qw);
	}
	
	/**
	 * 推荐最近医院
	 * @param arg
	 * @return
	 */
	public IPage<UHospital> findRecommend(BasicDTO arg) {
		Page<UHospital> page = new Page<>(arg.getPageNum(), arg.getPageSize());
		if(StringUtils.isEmpty(arg.getLat())) {
			return mf.getUHospitalMapper().selectPage(page, new QueryWrapper<UHospital>().eq("state", 1));
		}
		//经纬度
		 return	mf.getUHospitalMapper().findRecommend(page, arg.getLat(), arg.getLng());
	}
	
	
	

	public String insertUHospital(UHospital uh) {
		//if(StringUtils.isEmpty(uh.getPhone())) {
		//	throw new MissRequiredParamException(HintTitle.System.phone_no_norm);
		//}
		
		uh.setCreatesTime(new  Date());
		int is=mf.getUHospitalMapper().insert(uh);
		if(is<=0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		
		return null;
	}
	
	
	public String updateUHospital(UHospital uh) {
		if(StringUtils.isEmpty(uh.getId())) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		int is=mf.getUHospitalMapper().update(uh, new UpdateWrapper<UHospital>().eq("id", uh.getId()));
		if(is<=0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		return null;
	}

	public UHospital getHospitalRecord(String id){
		if(StringUtils.isEmpty(id)){
			throw new MissRequiredParamException(Constant.HOSPITAL_ID_EMPTY);
		}
		List<UHospital> hospitals = mf.getUHospitalMapper().selectList(new QueryWrapper<UHospital>().eq("id", id));
		if(hospitals == null || hospitals.size() == 0){
			throw new MissRequiredParamException(Constant.HOSPITAL_NOT_EXIST);
		}
		return hospitals.get(0);
	}
	
	
	/**
	 * 删除评价
	 * @return
	 */
	public Result updateEvaluat(String id, String optionUid) {
		UPatientEvaluation entity =new UPatientEvaluation();
		entity.setIsDelete(2);
		entity.setOptionUid(optionUid);
		mf.getUPatientEvaluationMapper().update(entity,new  UpdateWrapper<UPatientEvaluation>().eq("id",id));
		return new Result();
	}
	
	
	public Result queryEvaluatList(RegistMangerInput mangerInput){
		Map<String, String> paramMap = queryRulePriority(mangerInput);
		Page<EvaluationQueryResultVO> page = new Page(mangerInput.getPageNum(), mangerInput.getPageSize());
		IPage<EvaluationQueryResultVO> results = mf.getUHospitalMapper().queryEvaluatResultList(page, paramMap.get("startDate"), paramMap.get("endDate"), paramMap.get("hospitalId"));
		return new Result(results);
	}

	private Map<String, String> queryRulePriority(RegistMangerInput mangerInput){
		Map<String, String> ruleMap = null;
		String startDate = mangerInput.getStartDate();
		String endDate = mangerInput.getEndDate();
		String hospitalId = mangerInput.getHospitalId();
		Integer dateValue = mangerInput.getDateValue();
		if(StringUtils.isEmpty(hospitalId)){
			hospitalId = null;
		}
		//优先级，先按固定值查询
		if(dateValue != null){
			if(dateValue<1 || dateValue > 5){
				throw new MissRequiredParamException(Constant.QUERY_PARAM_INVALID);
			}
			//已就诊/已过期/已取消 1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月
			ruleMap = CommonUtil.getFinishEndDateByDateValue(dateValue);
		}else{
			ruleMap = CommonUtil.getDateQueryMethod(startDate, endDate);
		}
		ruleMap.put("hospitalId", hospitalId);
		return ruleMap;
	}

	
}
