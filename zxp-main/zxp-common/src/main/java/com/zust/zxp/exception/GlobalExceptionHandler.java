package com.zust.zxp.exception;

import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResultBean handler(RuntimeException e){
        log.error("运行时异常-------------->",e);
        return ResultBean.fail(ResponseStatus.ERROR);
    }

}
