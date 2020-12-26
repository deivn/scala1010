package com.ymw.love.system.config.excep;

import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Describe: 描述 全局捕获异常
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class UserExceptionHandler<ServiceException extends BizException> {
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
         result.setMsg("System exception："+e.getMessage());
        log.info("System exception==》"+e.getMessage());
        e.printStackTrace();
        return result;
    }
}
