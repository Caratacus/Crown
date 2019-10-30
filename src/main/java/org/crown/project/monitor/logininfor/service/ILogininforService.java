package org.crown.project.monitor.logininfor.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.logininfor.domain.Logininfor;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author Crown
 */
public interface ILogininforService extends BaseService<Logininfor> {

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<Logininfor> selectLogininforList(Logininfor logininfor);

}
