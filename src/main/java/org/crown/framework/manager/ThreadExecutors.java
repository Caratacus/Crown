package org.crown.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.crown.common.utils.Threads;
import org.crown.framework.spring.ApplicationUtils;

/**
 * 线程异步执行工具类
 *
 * @author Caratacus
 */
public abstract class ThreadExecutors {

    /**
     * 操作延迟10毫秒
     */
    private static final int OPERATE_DELAY_TIME = 10;

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public static void execute(TimerTask task) {
        getExecutorService().schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取ScheduledExecutorService
     *
     * @return
     */
    public static ScheduledExecutorService getExecutorService() {
        return ApplicationUtils.getBean(ScheduledExecutorService.class);
    }

    /**
     * 停止任务线程池
     */
    public static void shutdown() {
        Threads.shutdownAndAwaitTermination(getExecutorService());
    }
}
