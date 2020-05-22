package com.iqilu.config;

import com.iqilu.bean.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 *
 * @author zhangyicheng
 * @ControllerAdvice 处理开关
 * @date 2020/05/20
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionInterceptor {


    @ExceptionHandler(Exception.class)
    public Object interceptException(Exception e, HttpServletRequest request) {

        log.error("↓↓↓↓↓↓------------系统异常信息------------↓↓↓↓↓↓");
        log.error("异常信息捕获: ",e);
        log.error("↑↑↑↑↑↑------------系统异常信息------------↑↑↑↑↑↑");
///        e.printStackTrace();
        return Result.fail("500", "有错: " + e.getMessage());
    }
}
