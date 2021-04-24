package com.zust.zxp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.zust.zxp.valid.CreateGroup;
import com.zust.zxp.valid.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "id必须为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名
     */
    @NotBlank(groups = {CreateGroup.class},message = "用户名不能为空")
    @Null(groups = {UpdateGroup.class},message = "用户名不能修改")
    private String username;

    /**
     * 登录密码
     */
    @NotBlank(groups = {CreateGroup.class},message = "密码不能为空")
    @Null(groups = {UpdateGroup.class},message = "密码不能修改")
    private String password;

    /**
     * 昵称
     */
    @NotBlank(groups = {CreateGroup.class},message = "昵称不能为空")
    private String nickName;

    /**
     * 用户头像
     */
    private String userPhoto;

    /**
     * 用户性别，0：男，1：女
     */
    private Boolean userSex;

    /**
     * 学校
     */
    @NotBlank(groups = {CreateGroup.class},message = "学校不能为空")
    private String school;

    /**
     * 地址
     */
    @NotBlank(groups = {CreateGroup.class},message = "地址不能为空")
    private String address;

    /**
     * 用户状态，0：正常
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 微信登录openid
     */
    @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "Openid必须空")
    private String weixinOpenid;

}
