package org.crown.service.impl;

import org.crown.model.entity.User;
import org.crown.mapper.UserMapper;
import org.crown.service.IUserService;
import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

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

}
