package com.ymw.love.system.config.excep;

import com.ymw.love.system.common.SystemEnum;

/**
 * <pre>
 * 通用运行时异常,系统中其他运行时异常可以继承次运行时异常
 * </pre>
 *
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -1491379724629290016L;

    /**
     * 异常代码
     */
    private Integer code;

    /**
     * 是否为可忽略异常(默认不可忽略)
     * <p>
     */
    private boolean ignore = false;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
        this.code=SystemEnum.FAIL;
    }

    public ServiceException(Integer code, String message) {
        this(message);
        this.code = code;
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

 

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public ServiceException ignore() {
        this.ignore = true;
        return this;
    }
}
