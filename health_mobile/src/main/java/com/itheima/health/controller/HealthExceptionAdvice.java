package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/22/21:49
 */
@RestControllerAdvice
public class HealthExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(HealthExceptionAdvice.class);
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e ){
        return new Result(false,e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发出未知异常",e);
        return new Result(false,"发生未知异常");
    }

}
