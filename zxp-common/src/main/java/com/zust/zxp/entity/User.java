package com.zust.zxp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
    @Null
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名
     */
    @NotBlank
    private String username;

    /**
     * 登录密码
     */
    @NotBlank
    private String password;

    /**
     * 昵称
     */
    @NotBlank
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
    @NotBlank
    private String school;

    /**
     * 地址
     */
    @NotBlank
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


}
