package org.crown.framework.manager;

import javax.annotation.PreDestroy;

import org.crown.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author cj
 */
@Component
@Slf4j
public class ShutdownManager {

    @Autowired(required = false)
    private SpringSessionValidationScheduler springSessionValidationScheduler;

    @PreDestroy
    public void destroy() {
        shutdownSpringSessionValidationScheduler();
        shutdownAsyncManager();
    }

    /**
     * 停止Seesion会话检查
     */
    private void shutdownSpringSessionValidationScheduler() {
        if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled()) {
            try {
                log.info("Seesion会话检查 - 停止中");
                springSessionValidationScheduler.disableSessionValidation();
                log.info("Seesion会话检查 - 已停止");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager() {
        try {
            log.info("============关闭后台任务线程池============");
            ThreadExecutors.shutdown();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
