package org.crown.project.system.user.service;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.system.user.domain.UserRole;
import org.crown.project.system.user.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * 用户 业务层处理
 *
 * @author Crown
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
