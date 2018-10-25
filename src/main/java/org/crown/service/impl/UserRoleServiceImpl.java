package org.crown.service.impl;

import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.UserRoleMapper;
import org.crown.model.entity.UserRole;
import org.crown.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户角色关系表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
