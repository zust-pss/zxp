package com.zust.zxp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建者用户ID
     */
    private Integer user1Id;

    /**
     * 订单状态,0：正常,1：完成，2：过期，3：被撤销
     */
    private Boolean orderStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 地址
     */
    private String address;

    /**
     * 图片
     */
    private String picture;

    /**
     * 拼单用户ID
     */
    private Integer user2Id;


}
