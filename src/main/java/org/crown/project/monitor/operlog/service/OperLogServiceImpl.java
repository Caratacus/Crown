package org.crown.project.monitor.operlog.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.monitor.operlog.domain.OperLog;
import org.crown.project.monitor.operlog.mapper.OperLogMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 操作日志 服务层处理
 *
 * @author Crown
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

    @Override
    public List<OperLog> selectOperLogList(OperLog operLog) {
        Date beginTime = operLog.getBeginTime();
        Date endTime = operLog.getEndTime();
        return query().like(StringUtils.isNotEmpty(operLog.getTitle()), OperLog::getTitle, operLog.getTitle())
                .eq(Objects.nonNull(operLog.getBusinessType()), OperLog::getBusinessType, operLog.getBusinessType())
                .in(CollectionUtils.isNotEmpty(operLog.getBusinessTypes()), OperLog::getBusinessType, operLog.getBusinessTypes())
                .eq(Objects.nonNull(operLog.getStatus()), OperLog::getStatus, operLog.getStatus())
                .like(StringUtils.isNotEmpty(operLog.getOperName()), OperLog::getOperName, operLog.getOperName())
                .gt(Objects.nonNull(beginTime), OperLog::getOperTime, beginTime)
                .lt(Objects.nonNull(endTime), OperLog::getOperTime, endTime)
                .list();
    }

}
