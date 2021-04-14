package com.zust.zxp.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.entity.User;
import com.zust.zxp.enums.ResponseStatus;
import com.zust.zxp.exception.BusinessException;
import com.zust.zxp.mapper.UserMapper;
import com.zust.zxp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

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

    @Value("${md5.salt}")
    private String salt;


    public Integer login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id","password").eq("username",user.getUsername());
        User user1 = getOne(wrapper);
        if(user1 == null){
            throw new BusinessException(ResponseStatus.USERNAME_NOT_EXISTS);
        }

        if (user1.getPassword().equals(SaSecureUtil.md5BySalt(user.getPassword(),salt))){
            StpUtil.setLoginId(user1.getId());
        }else {
            throw new BusinessException(ResponseStatus.PASSWORD_ERROR);
        }

        return user.getId();
    }

    public Integer register(User user) {
        QueryWrapper<User> username = new QueryWrapper<User>().eq("username", user.getUsername());
        int count = count(username);
        if(count >= 1){
            throw new BusinessException(ResponseStatus.USERNAME_ALREADY_EXISTS);
        }
        user.setPassword(SaSecureUtil.md5BySalt(user.getPassword(),salt));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        save(user);
        StpUtil.setLoginId(user.getId());
        return user.getId();
    }


    public User getUserInfo(Integer id) {
        return getById(id);
    }
}
