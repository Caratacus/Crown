package org.crown.project.system.dict.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.dict.domain.DictData;

/**
 * 字典表 数据层
 *
 * @author Crown
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

}
