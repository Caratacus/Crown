package org.crown.project.monitor.online.service;

import java.util.Date;
import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.online.domain.UserOnline;

/**
 * 在线用户 服务层
 *
 * @author Crown
 */
public interface IUserOnlineService extends BaseService<UserOnline> {

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    UserOnline selectOnlineById(String sessionId);

    /**
     * 查询会话集合
     *
     * @param userOnline 分页参数
     * @return 会话集合
     */
    List<UserOnline> selectUserOnlineList(UserOnline userOnline);

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    void forceLogout(String sessionId);

    /**
     * 查询会话集合
     *
     * @param expiredDate 有效期
     * @return 会话集合
     */
    List<UserOnline> selectOnlineByExpired(Date expiredDate);
}
