package com.ymw.love.system.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月16日 下午4:24:50
*类说明：
*/

public class AuthorityVO {
	
	/**
	 * 角色选择权限
	 */
	@Getter
	@Setter
	private String optionCode;
	
	
	
	@Getter
	@Setter
	private String title;
    /**
     * 编号
     */
	@Getter
	@Setter
	private String code;
    /**
     * 标识  0不区分    1，菜单 、 2 二级菜单 3功能
     */
	@Getter
	@Setter
	private Integer menuType;
    /**
     * 父id
     */
	@Getter
	@Setter
	private Integer fatherId;
    /**
     * 拦截url地址
     */
	@Getter
	@Setter
	private String url;
	
 
	@Setter
    List<AuthorityVO> data;


	public List<AuthorityVO> getData() {
		
		if(data==null || data.size()<=0) {
		  return null;
		}
		return data;
	}
	
	
	
	
}
