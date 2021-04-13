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

        NO_LOGIN(1002, "未登录或登陆失效！");



        private int code;
        private String msg;

}
