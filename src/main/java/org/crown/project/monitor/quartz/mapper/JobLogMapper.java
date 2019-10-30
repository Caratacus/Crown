package org.crown.project.monitor.quartz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.crown.framework.mapper.BaseMapper;
import org.crown.project.monitor.quartz.domain.JobLog;

/**
 * <p>
 * 定时任务日志 Mapper 接口
 * </p>
 *
 * @author Caratacus
 */
@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {

}
