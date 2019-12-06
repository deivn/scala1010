package com.ymw.love.system.config.excep;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date : 17:32 2018/10/16
 * @Describe: 描述
 */
@Setter
@Getter
public class BizException extends RuntimeException {

    /**
     * code     返回码
     * msg      返回码描述
     * subCode  详细返回码
     * subMsg   详细返回码描述
     */
    protected Integer code;
    protected String msg;
    protected String token;
    protected Integer subCode ;

    public BizException(Integer code, String msg,Integer subCode){
        this.code = code;
        this.msg = msg;
        this.subCode=subCode;
    }

    public BizException(Integer code,String msg){
        this.code = code;
        this.msg = msg;
      
    }

    public BizException() {
        super();
    }


}

