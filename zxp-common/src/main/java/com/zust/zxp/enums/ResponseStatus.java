package com.zust.zxp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResponseStatus {


        /**
         * 请求成功
         * */
        OK(200,"SUCCESS"),

        /**
         * 服务器异常
         * */
        ERROR(500,"未知异常"),

        PASSWORD_ERROR(1001,"密码错误！"),

        USERNAME_ALREADY_EXISTS(1002,"用户名已存在"),

        USERNAME_NOT_EXISTS(1003,"用户不存在"),

        ARGUMENT_EXCEPTION(1004,"参数异常"),

        INVALID_PASSWORD(1005,"不合法的密码"),

        DIFFERENT_PASSWORD(1006,"两次密码不同"),


        NO_LOGIN(1020, "未登录或登陆失效！");



        private int code;
        private String msg;

}
