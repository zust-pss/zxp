package com.zust.zxp.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //运行时异常
    @ExceptionHandler(value = RuntimeException.class)
    public ResultBean handler(RuntimeException e){
        log.error("运行时异常-------------->",e);
        return ResultBean.error();
    }

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    public ResultBean handlerNotLoginException(NotLoginException nle) {
        // 返回给前端
        log.error("未登录异常-------------->",nle);
        return ResultBean.fail(ResponseStatus.NO_LOGIN);
    }

    // 自定义处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResultBean handlerBusinessExceptionn(BusinessException be) {
        // 返回给前端
        log.error("业务异常-------------->",be);
        return ResultBean.fail(be.getResStatus());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean handler(MethodArgumentNotValidException e){
        log.error("实体校验异常-------------->",e);

        String msg = e.getMessage();
        System.out.println(msg);
        if (msg != null) {
            int lastIndex1 = msg.lastIndexOf('[');
            if (lastIndex1 >= 0) {
                int lastIndex2 = msg.indexOf(']',lastIndex1);
                msg = msg.substring(lastIndex1 + 1,lastIndex2);
            }
        }
        return ResultBean.fail(ResponseStatus.ARGUMENT_EXCEPTION,msg);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultBean IllegalArgumentExceptionHandler(IllegalArgumentException e){
        log.error("参数异常-------------->",e);

        return ResultBean.fail(ResponseStatus.ARGUMENT_EXCEPTION);
    }
}
