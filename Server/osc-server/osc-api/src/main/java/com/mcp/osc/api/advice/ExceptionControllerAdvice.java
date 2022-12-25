package com.mcp.osc.api.advice;

import com.mcp.osc.infrastructure.api.Result;
import com.mcp.osc.infrastructure.web.BaseExceptionControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


/**
 * @author: KG
 * @description: 全局异常处理器
 * @date: Created in 5:56 下午 2022/9/21
 * @modified by: KG 2022/9/21
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends BaseExceptionControllerAdvice {
    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> constraintExceptionHandler(ConstraintViolationException e) {
        return super.constraintExceptionHandler(e);
    }

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return super.methodArgumentNotValidExceptionHandler(e);
    }

    @Override
    @ExceptionHandler(Exception.class)
    public Result<String> apiExceptionHandler(Exception e) {
        return super.apiExceptionHandler(e);
    }

}

