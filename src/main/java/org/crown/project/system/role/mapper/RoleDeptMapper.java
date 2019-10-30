package org.crown.project.system.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.role.domain.RoleDept;

/**
 * 角色与部门关联表 数据层
 *
 * @author Crown
 */
@Mapper
public interface RoleDeptMapper extends BaseMapper<RoleDept> {

}
