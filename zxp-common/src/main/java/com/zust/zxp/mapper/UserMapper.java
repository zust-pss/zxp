package com.zust.zxp.mapper;

import com.zust.zxp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
