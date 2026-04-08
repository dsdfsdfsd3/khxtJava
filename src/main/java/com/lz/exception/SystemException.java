package com.lz.exception;

public class SystemException extends RuntimeException{


    private Integer code ; //异常编号

    public SystemException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    public SystemException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
