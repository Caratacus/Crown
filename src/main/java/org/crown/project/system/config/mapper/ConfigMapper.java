package org.crown.project.system.config.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.system.config.domain.Config;

/**
 * 参数配置 数据层
 *
 * @author Crown
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}