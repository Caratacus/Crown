package org.crown.autoconfigure;

import org.crown.project.monitor.quartz.common.QuartzManage;
import org.crown.project.monitor.quartz.domain.Job;
import org.crown.project.monitor.quartz.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务初始化
 *
 * @author Caratacus
 */
@Component
@Slf4j
public class QuartzJobInitRunner implements ApplicationRunner {

    @Autowired
    private IJobService jobService;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 项目启动时重新激活启用的定时任务
     *
     * @param applicationArguments
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("Quartz定时任务 - 数据加载中");
        jobService.query().eq(Job::getPaused, false).list().forEach(quartzJob -> quartzManage.addJob(quartzJob));
        log.info("Quartz定时任务 - 数据加载完成");
    }
}
