package com.lz.exception;


import com.lz.tool.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //表示rest风格开发对异常做处理
//@ControllerAdvice  //普通风格对异常做处理
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)        //系统异常
    public Result SystemException(SystemException exception){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员
        return new Result(null,"601",exception.getMessage());

    }

    @ExceptionHandler(Exception.class)          //其他未知异常
    public  Result  doException(Exception ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员
        return new Result(null,"600","系统繁忙请稍后重试...");
    }
}