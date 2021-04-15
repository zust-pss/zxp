package com.zust.zxp.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.Dto.PasswordDto;
import com.zust.zxp.bean.UserInfo;
import com.zust.zxp.entity.User;
import com.zust.zxp.enums.ResponseStatus;
import com.zust.zxp.exception.BusinessException;
import com.zust.zxp.mapper.UserMapper;
import com.zust.zxp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Override
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

    @Override
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

    @Override
    public UserInfo getUserInfo(Integer id) {
        User user = getById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }

    @Override
    public UserInfo updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        updateById(user);
        UserInfo userInfo = getUserInfo(user.getId());
        return userInfo;
    }

    @Override
    public Boolean updatePassword(PasswordDto pDto) {
        String o1 = pDto.getOldPassword();
        String n1 = pDto.getNewPassword1();
        String n2 = pDto.getNewPassword2();
        if(StrUtil.isBlank(n1)) throw new BusinessException(ResponseStatus.INVALID_PASSWORD); //后续考虑设置密码的复杂度
        if(!n1.equals(n2)) throw new BusinessException(ResponseStatus.DIFFERENT_PASSWORD);

        Integer id = StpUtil.getLoginIdAsInt();

        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("id", id).select("password");
        User userInfo = getOne(wrapper);

        if (!userInfo.getPassword().equals(SaSecureUtil.md5BySalt(o1,salt))) throw new BusinessException(ResponseStatus.PASSWORD_ERROR);

        User user = new User();
        user.setId(id);
        user.setPassword(SaSecureUtil.md5BySalt(n1,salt));
        user.setUpdateTime(LocalDateTime.now());

        return updateById(user);
    }
}
