package com.ymw.love.system.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月16日 下午4:24:50
*类说明：
*/
@ApiModel(description = "权限返回实体")
public class AuthorityVO {
	
	/**
	 * 角色选择权限
	 */
	@Getter
	@Setter
	@ApiModelProperty("角色选择权限")
	private String optionCode;
	
	
	
	@Getter
	@Setter
	@ApiModelProperty("权限名字")
	private String title;
    /**
     * 编号
     */
	@Getter
	@Setter
	@ApiModelProperty("编号")
	private String code;
    /**
     * 标识  0不区分    1，菜单 、 2 二级菜单 3功能
     */
	@Getter
	@Setter
	@ApiModelProperty("标识  0不区分    1，菜单 、 2 二级菜单 3功能")
	private Integer menuType;
    /**
     * 父id
     */
	@Getter
	@Setter
	@ApiModelProperty(value="父id",hidden=true)
	private Integer fatherId;
	
    /**
     * 拦截url地址
     */
	@Getter
	@Setter
	@ApiModelProperty(value="拦截url地址",hidden=true)
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
