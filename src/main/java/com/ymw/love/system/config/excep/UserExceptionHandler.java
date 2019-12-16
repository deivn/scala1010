package com.ymw.love.system.config.excep;


import com.ymw.love.system.util.BeanValidatorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.HintTitle;
import com.ymw.love.system.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;


/**
 * @Describe: 描述 全局捕获异常
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class UserExceptionHandler<ServiceException extends BizException> {
   
	@Value("${environ}")
	private String environ;
	
	
	@ExceptionHandler(BizException.class)  //业务抛出的异常
    public Result unknowHandler(ServiceException serviceException) {
        Result result = new Result();
        result.setCode(serviceException.getCode());
        result.setMsg(serviceException.getMsg());
        log.info("Business exception==》"+serviceException.getMsg());
        return result;
    }

    @ExceptionHandler(Exception.class)  //系统抛出的未知异常
    public Result unknowHandler(Exception e) {
        Result result = new Result();
        result.setCode(SystemEnum.SYSTEM_ERROR);
        if(e instanceof HttpMessageNotReadableException) { //传参不规范异常
            result.setMsg(HintTitle.System.parameter_format_error);
        }else {
        	//判断是否环境，是否要返回异常详情
        	if(StringUtils.isEmpty(environ) || !Boolean.parseBoolean(environ)) {
        		result.setMsg("System exception："+e.getMessage());
        	}else {
        		result.setMsg("系统异常");
        	}

        }
        log.error("接口异常",e.getMessage());
        e.printStackTrace();
        return result;
    }


    /**
     * 验证单个参数
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handlerConstraintViolationException(ConstraintViolationException e) {
        log.error("参数检验错误",e.getMessage());
        Result result = new Result();
        result.setCode(SystemEnum.FAIL);
        result.setMsg(BeanValidatorUtil.getErrorMessage(e));
        return result;
    }


    /**
     * 验证对象属性
     * @RequestBody
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("实体属性验证错误",e.getMessage());
        Result result = new Result();
        result.setCode(SystemEnum.FAIL);
        result.setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }
}
