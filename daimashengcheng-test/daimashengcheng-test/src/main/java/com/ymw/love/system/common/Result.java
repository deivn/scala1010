package com.ymw.love.system.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 */
@ApiModel(description = "统一返回实体")
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -8093053814385374971L;

	@ApiModelProperty("返回码")
	private Integer code = 0;

	@ApiModelProperty("信息描述")
	private String msg;

	@ApiModelProperty("用户token")
	@Getter
	private String token;

	@ApiModelProperty("返回数据")
	private T data;

	@ApiModelProperty(hidden=true,value="移除对应字段")
	@Getter
	private String[] remove;
	
	@ApiModelProperty(hidden=true,value="保留对应字段")
	@Getter
	private String[]  retain;
	
	
	
	public Result() {
		this.setCode(SystemEnum.SUCCESS);
	}

	public Result(T t) {
		if(t != null) {
			this.setData(t);
		}
		this.setCode(SystemEnum.SUCCESS);
	}

	public Result setCode(Integer code) {
		this.code = code;
		return this;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public T getData() {
		return this.data;
	}

	public Result setData(T data) {
		this.data = data;
		return this;
	}

	public Result setToken(String token) {
		this.token = token;
		return this;
	}
	
	
	public  Result setRemove(String ...value) {
		this.remove=value;
		return this;
	}
	
	
	public Result setRetain(String ...value) {
		this.retain=value;
		return this;
	}
	
}
