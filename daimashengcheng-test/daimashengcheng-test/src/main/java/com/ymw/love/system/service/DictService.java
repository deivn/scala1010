package com.ymw.love.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.SysDictBase;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.entity.AUser;
import com.ymw.love.system.entity.SysDict;
import com.ymw.love.system.entity.faq.FaqEntity;
import com.ymw.love.system.immobile.HintTitle;
import com.ymw.love.system.mapper.AUserMapper;
import com.ymw.love.system.mapper.sys.SysDictMapper;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月15日 
*类说明：字典操作
*/
@Service
public class DictService extends BaseService{

	
	
    
	public IPage<SysDict> findSysDictList(BaseEntity be,Integer type){
		  Page<SysDict> page = new Page<>(be.getPageNum(), be.getPageSize());
		 return mf.getSysDictMapper().selectPage(page,new QueryWrapper<SysDict>().eq("type", type).eq("state", 1));
	}
	
	public List<SysDict> findSysDictListAll(Integer type){
		return mf.getSysDictMapper().selectList(new QueryWrapper<SysDict>().eq("type", type).eq("state", 1));
	}
	
	
	
	public String updateSysDict(SysDict sd) {
		if(sd==null || sd.getId()==null) {
			throw new MissRequiredParamException(HintTitle.System.parameter_is_null);
		}
		int is= mf.getSysDictMapper().update(sd, new UpdateWrapper<SysDict>().eq("id", sd.getId()).eq("type", sd.getType()));
		if(is<=0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		return null;
	}
	
	/**
	 * 删除部门\职称
	 * @param id
	 * @param type
	 * @return
	 */
	public String deleteSysDict(Integer id,Integer type ) {
		SysDict sd =new SysDict();
		sd.setState(3);//删除状态
		int is= mf.getSysDictMapper().update(sd,new UpdateWrapper<SysDict>().eq("id", id).eq("type", type));
		if(is<=0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		
		if(type.equals(SysDictBase.SysType.position)) {
			//清空用户对应职称
			mf.getAUserMapper().updateEmptySectorPosition(SysDictBase.SysType.position, id);
		}else if(type.equals(SysDictBase.SysType.sector)) {//部门
			//清空用户对应部门
			mf.getAUserMapper().updateEmptySectorPosition(SysDictBase.SysType.sector, id);
		}
		
	     return null;	
	}
	
	public String insertSysDict(SysDict sd) {
		 sd.setCreatesTime(new Date());
		int is= mf.getSysDictMapper().insert(sd);
		if(is<=0) {
			throw new MissRequiredParamException(HintTitle.System.failed);
		}
		return null;
	}
	
	
}
