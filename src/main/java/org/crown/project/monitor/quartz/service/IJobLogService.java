package org.crown.project.monitor.quartz.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.quartz.domain.JobLog;

/**
 * <p>
 * 定时任务日志 服务类
 * </p>
 *
 * @author Caratacus
 */
public interface IJobLogService extends BaseService<JobLog> {

    /**
     * 查询定时任务日志列表
     *
     * @param jobLog
     * @return
     */
    List<JobLog> selectJobLogList(JobLog jobLog);
}
