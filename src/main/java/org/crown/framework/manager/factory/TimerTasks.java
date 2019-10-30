package org.crown.framework.manager.factory;

import java.util.Date;
import java.util.TimerTask;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.Constants;
import org.crown.common.utils.Crowns;
import org.crown.common.utils.IpUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.springboot.properties.Email;
import org.crown.project.monitor.exceLog.domain.ExceLog;
import org.crown.project.monitor.exceLog.service.IExceLogService;
import org.crown.project.monitor.logininfor.domain.Logininfor;
import org.crown.project.monitor.logininfor.service.ILogininforService;
import org.crown.project.monitor.online.domain.OnlineSession;
import org.crown.project.monitor.online.domain.UserOnline;
import org.crown.project.monitor.online.service.IUserOnlineService;
import org.crown.project.monitor.operlog.domain.OperLog;
import org.crown.project.monitor.operlog.service.IOperLogService;

import cn.hutool.extra.mail.MailUtil;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * TimerTask异步 工具类
 *
 * @author Caratacus
 */
@Slf4j
public class TimerTasks {

    /**
     * 同步session到数据库
     *
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSession(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                UserOnline online = new UserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(IpUtils.getRealAddress(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                online.setSession(session);
                ApplicationUtils.getBean(IUserOnlineService.class).saveOrUpdate(online);

            }
        };
    }

    /**
     * 同步session到数据库
     *
     * @param ip      IP地址
     * @param exceLog 异常日志
     * @return 任务task
     */
    public static TimerTask saveExceLog(final String ip, final int status, final ExceLog exceLog) {
        return new TimerTask() {
            @Override
            public void run() {
                Email email = Crowns.getEmail();
                if (status >= HttpServletResponse.SC_INTERNAL_SERVER_ERROR && email.isEnabled()) {
                    MailUtil.send(email.getSend(), "Crown2系统的异常告警", exceLog.getContent(), false);
                }
                exceLog.setIpAddr(IpUtils.getRealAddress(ip));
                ApplicationUtils.getBean(IExceLogService.class).save(exceLog);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(IpUtils.getRealAddress(operLog.getOperIp()));
                operLog.setOperTime(new Date());
                ApplicationUtils.getBean(IOperLogService.class).save(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ApplicationUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = IpUtils.getRealAddress(ip);
                StringBuilder s = new StringBuilder();
                s.append(getBlock(ip));
                s.append(address);
                s.append(getBlock(username));
                s.append(getBlock(status));
                s.append(getBlock(message));
                // 打印信息到日志
                log.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                Logininfor logininfor = new Logininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                logininfor.setLoginTime(new Date());
                // 插入数据
                ApplicationUtils.getBean(ILogininforService.class).save(logininfor);
            }
        };
    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
