package org.crown.service.impl;

import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.RoleMapper;
import org.crown.model.entity.Role;
import org.crown.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

}
