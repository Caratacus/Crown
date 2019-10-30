package org.crown.project.system.dict.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.dict.domain.DictType;

/**
 * 字典表 数据层
 *
 * @author Crown
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

}
