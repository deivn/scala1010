package com.ymw.love.system.vo;

import java.util.List;

import com.ymw.love.system.entity.authority.SysRole;

import lombok.Data;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月16日 
*类说明：返回详情
*/
@Data
public class RoleDetailsVO  extends SysRole{
	
	private List<AuthorityVO> authorit ;
	
	
	
}
