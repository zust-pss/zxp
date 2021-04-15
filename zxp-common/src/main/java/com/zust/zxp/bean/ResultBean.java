package com.zust.zxp.bean;

import com.zust.zxp.enums.ResponseStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code = ResponseStatus.OK.getCode();

    /**
     * 响应消息
     * */
    private String msg = ResponseStatus.OK.getMsg();
    /**
     * 响应中的数据
     * */
    private T data;

    private ResultBean() {

    }

    private ResultBean(ResponseStatus ResponseStatus) {
        this.code = ResponseStatus.getCode();;
        this.msg = ResponseStatus.getMsg();
    }

    private ResultBean(ResponseStatus ResponseStatus,T data) {
        this.code = ResponseStatus.getCode();;
        this.msg = ResponseStatus.getMsg();
        this.data = data;
    }

    private ResultBean(T data) {
        this.data = data;
    }



    /**
     * 业务处理成功,无数据返回
     * */
    public static ResultBean ok() {
        return new ResultBean();
    }

    /**
     * 业务处理成功，有数据返回
     * */
    public static <T> ResultBean ok(T data) {
        return new ResultBean(data);
    }

    /**
     * 业务处理失败
     * */
    public static ResultBean fail(ResponseStatus ResponseStatus) {
        return new ResultBean(ResponseStatus);
    }

    /**
     * 业务处理失败,有数据返回
     * */
    public static <T> ResultBean fail(ResponseStatus ResponseStatus,T data) {
        return new ResultBean(ResponseStatus,data);
    }


    /**
     * 系统错误
     * */
    public static ResultBean error() {
        return new ResultBean(ResponseStatus.ERROR);
    }
}
