package org.crown.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.crown.common.cons.Constants;
import org.crown.common.cons.ShiroConstants;
import org.crown.common.utils.Crowns;
import org.crown.framework.exception.Crown2Exception;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录密码方法
 *
 * @author Crown
 */
@Component
public class PasswordService {

    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    public void validate(User user, String password) {
        int maxRetryCount = Crowns.getMaxRetryCount();
        String loginName = user.getLoginName();
        AtomicInteger retryCount = loginRecordCache.get(loginName);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > maxRetryCount) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(loginName, Constants.LOGIN_FAIL, "密码输入错误" + maxRetryCount + "次，帐户锁定10分钟"));
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "密码输入错误" + maxRetryCount + "次，帐户锁定10分钟");
        }
        if (!matches(user, password)) {
            ThreadExecutors.execute(TimerTasks.recordLogininfor(loginName, Constants.LOGIN_FAIL, "密码输入错误" + retryCount + "次"));
            loginRecordCache.put(loginName, retryCount);
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "密码输入错误" + retryCount + "次");
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt) {
        return new Md5Hash(username + password + salt).toHex();
    }

   /* public static void main(String[] args) {
        System.out.println(new PasswordService().encryptPassword("admin", "admin123", "111111"));
        System.out.println(new PasswordService().encryptPassword("ry", "admin123", "222222"));
    }*/
}
