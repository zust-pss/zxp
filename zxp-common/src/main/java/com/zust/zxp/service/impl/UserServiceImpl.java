package com.zust.zxp.service.impl;

import com.zust.zxp.entity.User;
import com.zust.zxp.mapper.UserMapper;
import com.zust.zxp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
