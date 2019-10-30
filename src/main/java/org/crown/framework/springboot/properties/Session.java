package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Session {

    /**
     * # Session超时时间，-1代表永不过期（默认30分钟）
     */
    private int expireTime;
    /**
     * 同步session到数据库的周期（分钟）
     */
    private int dbSyncPeriod;
    /**
     * 相隔多久检查一次session的有效性，(分钟)
     */
    private int validationInterval;
    /**
     * 同一个用户最大会话数，比如2的意思是同一个账号允许最多同时两个人登录（-1不限制）
     */
    private int maxSession;
    /**
     * 踢出之前登录的/之后登录的用户，默认踢出之前登录的用户
     */
    private boolean kickoutAfter;
}
