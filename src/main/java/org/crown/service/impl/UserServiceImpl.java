package org.crown.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.crown.common.api.ApiAssert;
import org.crown.common.emuns.ErrorCodeEnum;
import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.crown.common.mybatisplus.Wrappers;
import org.crown.emuns.UserStatusEnum;
import org.crown.mapper.UserMapper;
import org.crown.model.entity.User;
import org.crown.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Override
    @Transactional
    public User login(String loginName, String password, String ipAddr) {
        User user = getOne(Wrappers.<User>query().eq(User.LOGIN_NAME, loginName));
        //用户不存在
        ApiAssert.notNull(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, user);
        //用户名密码错误
        ApiAssert.isTrue(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, Md5Crypt.apr1Crypt(password, loginName).equals(user.getPassword()));
        //用户被禁用
        ApiAssert.isTrue(ErrorCodeEnum.USER_IS_DISABLED, UserStatusEnum.NOMAL.equals(user.getStatus()));
        user.setIp(ipAddr);
        updateById(user);
        return user;
    }

}
