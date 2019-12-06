package com.ymw.love.system.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月15日 下午6:06:12
*类说明：后台管理员
*/
@Data
public class AUserVO {

	private String id;
	
	private String name;
	
	private String userName;
	
	
	private String hospitalId;
	
	/**
	 * 角色查看数据权限  1全部，2分部
	 */
	private Integer dataTier;
	
	/**
	 * 对应权限
	 */
	private Map<String, String> authoriUrl;
	
	private List<AuthorityVO> authori;
	
	
	
	
	
	
	
	
}
