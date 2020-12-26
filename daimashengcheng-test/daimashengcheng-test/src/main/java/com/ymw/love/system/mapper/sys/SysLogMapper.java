package com.ymw.love.system.mapper.sys;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.dto.BasicDTO;
import com.ymw.love.system.entity.SysLog;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月26日 
*类说明：
*/
public interface SysLogMapper  extends BaseMapper<SysLog>{

	
	IPage<Map<String, Object>> findList(Page page,   @Param("arg")BasicDTO arg);
	
	
}
