package org.crown.project.monitor.logininfor.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.project.monitor.logininfor.domain.Logininfor;
import org.crown.project.monitor.logininfor.mapper.LogininforMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author Crown
 */
@Service
public class LogininforServiceImpl extends BaseServiceImpl<LogininforMapper, Logininfor> implements ILogininforService {

    @Override
    public List<Logininfor> selectLogininforList(Logininfor logininfor) {
        Date beginTime = logininfor.getBeginTime();
        Date endTime = logininfor.getEndTime();
        return query().select()
                .like(StringUtils.isNotEmpty(logininfor.getIpaddr()), Logininfor::getIpaddr, logininfor.getIpaddr())
                .eq(Objects.nonNull(logininfor.getStatus()), Logininfor::getStatus, logininfor.getStatus())
                .like(StringUtils.isNotEmpty(logininfor.getLoginName()), Logininfor::getLoginName, logininfor.getLoginName())
                .ge(Objects.nonNull(beginTime), Logininfor::getLoginTime, beginTime)
                .le(Objects.nonNull(endTime), Logininfor::getLoginTime, endTime)
                .list();
    }

}
