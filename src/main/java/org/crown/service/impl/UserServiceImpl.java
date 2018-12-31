/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.collections4.CollectionUtils;
import org.crown.common.utils.JWTUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.enums.StatusEnum;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.framework.utils.ApiAssert;
import org.crown.mapper.UserMapper;
import org.crown.model.dto.TokenDTO;
import org.crown.model.dto.UserDetailsDTO;
import org.crown.model.entity.User;
import org.crown.model.entity.UserRole;
import org.crown.service.IResourceService;
import org.crown.service.IUserRoleService;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author Caratacus
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    @Transactional
    public User login(String loginName, String password, String ipAddr) {
        User user = query().eq(User::getLoginName, loginName).getOne();
        //用户不存在
        ApiAssert.notNull(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, user);
        //用户名密码错误
        ApiAssert.isTrue(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_WRONG, Md5Crypt.apr1Crypt(password, loginName).equals(user.getPassword()));
        //用户被禁用
        ApiAssert.isTrue(ErrorCodeEnum.USER_IS_DISABLED, StatusEnum.NORMAL.equals(user.getStatus()));
        user.setIp(ipAddr);
        updateById(user);
        return user;
    }

    @Override
    public TokenDTO getToken(User user) {
        Integer uid = user.getId();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUid(uid);
        tokenDTO.setToken(JWTUtils.generate(uid));
        return tokenDTO;
    }

    @Override
    public UserDetailsDTO getUserDetails(Integer uid) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        UserDetailsDTO userDetails = user.convert(UserDetailsDTO.class);
        userDetails.setPerms(resourceService.getUserPerms(uid));
        return userDetails;
    }

    @Override
    @Transactional
    public void updatePassword(Integer uid, String oldPassword, String newPassword) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        //用户名密码错误
        ApiAssert.isTrue(ErrorCodeEnum.ORIGINAL_PASSWORD_IS_INCORRECT, Md5Crypt.apr1Crypt(oldPassword, user.getLoginName()).equals(user.getPassword()));
        user.setPassword(Md5Crypt.apr1Crypt(newPassword, user.getLoginName()));
        updateById(user);
    }

    @Override
    @Transactional
    public void resetPwd(Integer uid) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        user.setPassword(Md5Crypt.apr1Crypt(user.getLoginName(), user.getLoginName()));
        updateById(user);
    }

    @Override
    @Transactional
    public void updateStatus(Integer uid, StatusEnum status) {
        User user = getById(uid);
        ApiAssert.notNull(ErrorCodeEnum.USER_NOT_FOUND, user);
        user.setStatus(status);
        updateById(user);
    }

    @Override
    @Transactional
    public void saveUserRoles(Integer uid, List<Integer> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            userRoleService.delete().eq(UserRole::getUid, uid).execute();
            userRoleService.saveBatch(roleIds.stream().map(e -> new UserRole(uid, e)).collect(Collectors.toList()));
        }
    }

    @Override
    public List<Integer> getRoleIds(Integer uid) {
        return userRoleService.query().select(UserRole::getRoleId).eq(UserRole::getUid, uid).listObjs(TypeUtils::castToInt);
    }

}
