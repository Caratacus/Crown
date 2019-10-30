package org.crown.project.system.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.role.domain.RoleMenu;

/**
 * 角色与菜单关联表 数据层
 *
 * @author Crown
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
