package org.crown.project.monitor.operlog.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.operlog.domain.OperLog;

/**
 * 操作日志 服务层
 *
 * @author Crown
 */
public interface IOperLogService extends BaseService<OperLog> {

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<OperLog> selectOperLogList(OperLog operLog);

}
