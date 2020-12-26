package com.ymw.love.system.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.UHospital;
import com.ymw.love.system.entity.seeDoctor.input.RegistMangerInput;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月15日
*类说明：
*/
@Controller
@RequestMapping("/admin/hospital")
@ResponseBody
@Api(tags = { "后台医院分部" })
public class HospitalController extends BaseService  {
 

	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "获取医院", notes = "分页")
	@PostMapping("/list")
	public Result findAllList(@RequestBody BaseEntity be) {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		//设置数据权限
		String hospitalId=au.getDataTier()==2?au.getHospitalId():null;
		IPage<UHospital> ip=sf.getHospitalService().findAll(be,hospitalId);
		return new Result(ip).setRemove(new String[] {"orders","searchCount"});
	}
	
	/**
	 * 不需要权限
	 * @param
	 * @return
	 */
	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@ApiOperation(value = "获取医院 不带分页", notes = "")
	@PostMapping("/find/list")
	public Result findList() {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		//设置数据权限
		String hospitalId=au.getDataTier()==2?au.getHospitalId():null;
		return new Result(sf.getHospitalService().findAllId(hospitalId)).setRetain(new String[] {"title","id"});
	}
	
	
	
	
	@Authority(value=Resource.enter.admin_user,content="添加分部")
	@ApiOperation(value = "添加分部", notes = "")
	@PostMapping("/ins")
	public Result insertUHospital(@RequestBody UHospital uh) {
		sf.getHospitalService().insertUHospital(uh);
		return new Result();
	}
	
	@Authority(value=Resource.enter.admin_user,content="删除分部")
	@ApiOperation(value = "删除分部", notes = "ids")
	@PostMapping("/del")
	public Result deleteUHospital(@RequestBody BasicDTO arg) {
		UHospital uh =new UHospital();
		uh.setId(arg.getIds());
		uh.setState(3);
		sf.getHospitalService().updateUHospital(uh);
		return new Result();
	}
	
	
	@Authority(value=Resource.enter.admin_user,content="修改分部")
	@ApiOperation(value = "修改分部", notes = "")
	@PostMapping("/upd")
	public Result updateUHospital(@RequestBody UHospital uh) {
		sf.getHospitalService().updateUHospital(uh);
		return new Result();
	}

	@Authority(value=Resource.enter.admin_user,holdUp=false)
	@ApiOperation(value = "获取医院信息", notes = "")
	@PostMapping("/selectRecordById")
	public Result selectRecordById(@RequestParam("id") String hospitalId) {
		UHospital uHospital = sf.getHospitalService().getHospitalRecord(hospitalId);
		return new Result(uHospital).setRemove(new String[] {"orders","searchCount"});
	}
	
	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "医院评价列表查询", notes = "")
	@PostMapping("/query/evaluation/list")
	public Result selectEvaluationList(@RequestBody RegistMangerInput mangerInput) {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		//设置数据权限
		String hospitalId=au.getDataTier()==2?au.getHospitalId():null;
		
		if(StringUtils.isEmpty(mangerInput.getHospitalId()) && StringUtils.isNotEmpty(hospitalId)) {
			mangerInput.setHospitalId(hospitalId);	
		}
		
		//1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月
		return sf.getHospitalService().queryEvaluatList(mangerInput);
	}

	@Authority(Resource.enter.admin_user)
	@ApiOperation(value = "医院评价删除", notes = "")
	@GetMapping("/delete/evaluation")
	public Result selectEvaluationList(@RequestParam(value = "id", required = true) String id) {
		AUserVO au=	sf.getUserLogInInfo().getAdminUser();
		//1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月
		return sf.getHospitalService().updateEvaluat(id, au.getId());
	}
}
