package org.crown.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.common.framework.mapper.BaseMapper;
import org.crown.model.entity.Role;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
