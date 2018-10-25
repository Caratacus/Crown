package org.crown.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.common.framework.mapper.BaseMapper;
import org.crown.model.entity.RoleMenu;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
