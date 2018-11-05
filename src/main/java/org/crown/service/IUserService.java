package org.crown.service;

import org.crown.common.framework.service.BaseService;
import org.crown.model.entity.User;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
public interface IUserService extends BaseService<User> {

    /**
     * 用户登陆
     *
     * @param loginName
     * @param password
     * @param ipAddr
     * @return
     */
    User login(String loginName, String password, String ipAddr);
}
