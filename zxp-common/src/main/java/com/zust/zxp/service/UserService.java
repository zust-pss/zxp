package com.zust.zxp.service;

import com.zust.zxp.Dto.PasswordDto;
import com.zust.zxp.bean.UserInfo;
import com.zust.zxp.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
public interface UserService extends IService<User> {
    Integer login(User user);

    Integer register(User user);

    UserInfo getUserInfo(Integer id);

    UserInfo updateUserInfo(User user);

    Boolean updatePassword(PasswordDto pDto);
}
