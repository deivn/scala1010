package com.ymw.love.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 医院
 * </p>
 *
 * @author 
 * @since 2019-08-15
 */
@TableName("u_hospital")
@Data
public class UHospital {

	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
    /**
     * 标题
     */
	private String title;
    /**
     * 地址
     */
	private String site;
    /**
     * 手机号码 多与逗号隔开
     */
	private String phone;
	
	/**
	 * 多张与逗号隔开
	 */
	@TableField("image_url")
	private String imageUrl;
    /**
     * 状态：1正常、2停用，3删除
     */
	private Integer state;
    /**
     * 创建时间
     */
	@TableField("creates_time")
	private Date createsTime;

	/**
	 * 纬度
	 */
    private Double lat;
    
    /**
     * 经度
     */
    private Double lng;
	

}
