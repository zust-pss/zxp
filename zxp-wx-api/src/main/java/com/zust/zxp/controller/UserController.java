package com.zust.zxp.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.Dto.PasswordDto;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.bean.UserInfo;
import com.zust.zxp.bean.WxLoginInfo;
import com.zust.zxp.entity.User;
import com.zust.zxp.enums.ResponseStatus;
import com.zust.zxp.exception.BusinessException;
import com.zust.zxp.service.UserService;
import com.zust.zxp.valid.CreateGroup;
import com.zust.zxp.valid.UpdateGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WxMaService wxService;

    /**
     * 登陆
     */
    @PostMapping("login")
    public ResultBean login(@RequestBody User user) {
        if(user.getUsername() == null || user.getPassword() == null)throw new BusinessException(ResponseStatus.ARGUMENT_EXCEPTION);
        //登陆
        userService.login(user);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        Map<String, Object> data = new HashMap<>(1);
        data.put("token",tokenInfo);
        return ResultBean.ok(data);
    }


    /**
     * 注册
     */
    @PostMapping("register")
    public ResultBean register(@Validated(value = CreateGroup.class) @RequestBody User user) {


        //注册
        userService.register(user);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        Map<String, Object> data = new HashMap<>(1);
        data.put("token", tokenInfo);

        return ResultBean.ok(data);


    }

    /**
     * 微信登录
     */
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        if (code == null) {
            throw new IllegalArgumentException();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            log.error("微信登录,调用官方接口失败：{}", code);

            throw new BusinessException(ResponseStatus.ERROR);
        }

        QueryWrapper<User> weixin_openid = new QueryWrapper<User>().eq("weixin_openid", openId);
        User user = userService.getOne(weixin_openid);

        if (user == null) {
            user = new User();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setNickName(wxLoginInfo.getNickName());
            user.setUserPhoto(wxLoginInfo.getAvatarUrl());
            user.setUserSex(wxLoginInfo.getGender());

            userService.save(user);

            Integer id = userService.getOne(new QueryWrapper<User>().select("id").eq("weixin_openid", user.getWeixinOpenid())).getId();

            user.setId(id);
        }
        StpUtil.setLoginId(user.getId());

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("token",StpUtil.getTokenValue());
        
        return ResultBean.ok(data);
    }

    /**
     * 获取用户信息
     */
    @SaCheckLogin
    @PostMapping("getUserInfo")
    public ResultBean getUserInfo() {
        Integer id = StpUtil.getLoginIdAsInt();
        UserInfo userInfo = userService.getUserInfo(id);

        Map<String, Object> data = new HashMap<>(1);
        data.put("userInfo", userInfo);

        return ResultBean.ok(data);
    }

    /**
     * 更新用户信息
     */
    @SaCheckLogin
    @PostMapping("updateUserInfo")
    public ResultBean updateUserInfo(@Validated(value = UpdateGroup.class) @RequestBody User user) {

        Integer id = StpUtil.getLoginIdAsInt();
        user.setId(id);
        UserInfo userInfo = userService.updateUserInfo(user);

        Map<String, Object> data = new HashMap<>(1);
        data.put("userInfo", userInfo);

        return ResultBean.ok(data);
    }

    /**
     * 修改密码
     */
    @SaCheckLogin
    @PostMapping("updatePassword")
    public ResultBean updatePassword(@RequestBody PasswordDto passwordDto) {

        if (!userService.updatePassword(passwordDto)) throw new BusinessException(ResponseStatus.ERROR);

        return ResultBean.ok();
    }

    /**
     * 获取历史订单
     */
    @SaCheckLogin
    @PostMapping("getHistoryOrder")
    public ResultBean getHistoryOrder() {

        //待完成

        return null;
    }

}
