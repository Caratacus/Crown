package org.crown.project.monitor.exceLog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.exceLog.domain.ExceLog;

/**
 * 异常日志 数据层
 *
 * @author Caratacus
 */
@Mapper
public interface ExceLogMapper extends BaseMapper<ExceLog> {

}