package com.hb.file.dispatch.controller;

import com.hb.file.core.commons.Result;
import com.hb.file.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * <a href="https://www.cnblogs.com/chentianming/p/13424303.html">Spring Validation最佳实践及其实现原理</a>
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String field = bindingResult.getFieldErrors().get(0).getField();
        String fieldMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        String message = "参数校验失败, " + field + fieldMessage;
        return Result.error(-1, message);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        String exceptionMsg = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.joining(","));
        return Result.error(-1, "参数校验失败: " + exceptionMsg);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleConstraintViolationException(MissingServletRequestParameterException exception) {
        return Result.error(-1, "参数校验失败: " + exception.getParameterName());
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleBusinessException(BusinessException exception) {
        return Result.error(-1, exception.getMessage());
    }
}
