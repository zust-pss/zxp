package com.zust.zxp.bean;

import lombok.Data;

@Data
public class WxLoginInfo {
    private String code;

    private String nickName;

    private String avatarUrl;

    private Boolean gender;
}
