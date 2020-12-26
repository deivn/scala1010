package com.ymw.love.system.dto;

import lombok.Data;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月15日 类说明：传递参数实例
 */
@Data
public class BasicDTO extends BaseEntity {

	/**
	 * 
	 */
	private Integer id;

	
	private String ids;
	
	
	private String code;
	
	
	/**
	 * 权限编码
	 */
	private String authoritCode;
	
	
	/**
	 * 描述
	 */
	private String depict;
	
	
	 /**
     * 名字
     */
	private String name;
	
	
	/**
	 * 数据级别：1全部，2部分
	 */
	private Integer dataTier;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 类型
	 */
	private Integer type;
	
	/**
	 * 时间类型
	 */
	private Integer timeType;
	
	/**
	 * 维度
	 */
	private String lat;
	
	/**
	 * 经度
	 */
	private String lng;

}
