package com.ymw.love.system.dto;

import java.io.Serializable;
import java.util.Date;

import com.ymw.love.system.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页
 */
@ApiModel(description = "分页基础类")
@Setter
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页数
     */
    @Getter
    @ApiModelProperty(value = "分页大小")
    private Integer pageNum = 1;

    /**
     * 每页显示数
     */
    @Getter
    @ApiModelProperty(value = "当前页")
    private Integer pageSize = 10;
    
    
	
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
 	private String startTime;
 	
 	
 	/**
 	 * 结束时间
 	 */
    @ApiModelProperty(value = "结束时间")
 	private String  endTime;

 	
 	 @ApiModelProperty(value = "今天：1、昨天/明天：2  、3天：3、7天：4、一个月：5")
	 @Getter
     private Integer day;
   
	  public String getStartTime() {
		
		if(StringUtils.isNotEmpty(startTime)) {
		  return startTime+" 00:00:01";
		}
		
		return startTime;
	}


	public String getEndTime() {
	   if(StringUtils.isNotEmpty(endTime)) {
		   return endTime+" 23:59:59";
		}
		return endTime;
	} 

 	
 	
 	
 	
 	
 	
}
