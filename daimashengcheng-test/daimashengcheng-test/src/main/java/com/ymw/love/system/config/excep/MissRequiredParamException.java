package com.ymw.love.system.config.excep;

import com.ymw.love.system.common.SystemEnum;

/**
 * @Date : 17:33 2018/10/16
 * @Describe: 描述
 */
public class MissRequiredParamException extends BizException {

    public MissRequiredParamException(Integer code, String subMsg) {
        super.code = code;
        super.msg = subMsg;
        
    }
    
    /**
     * 失败
     * @param subMsg
     */
    public MissRequiredParamException( String subMsg) {
        super.code =SystemEnum.FAIL;
        super.msg = subMsg;
        
    }
    
    
    
    public MissRequiredParamException(Integer code,Integer subCode, String subMsg) {
        super.code = code;
        super.msg = subMsg;
        super.subCode=subCode;
    }


    public MissRequiredParamException() {
        super();
    }
}
