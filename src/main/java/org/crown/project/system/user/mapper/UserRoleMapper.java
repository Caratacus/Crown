package org.crown.project.system.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.user.domain.UserRole;

/**
 * 用户表 数据层
 *
 * @author Crown
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
