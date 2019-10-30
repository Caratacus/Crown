package org.crown.project.monitor.quartz.common;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.QuartzCons;
import org.crown.framework.exception.Crown2Exception;
import org.crown.project.monitor.quartz.domain.Job;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Quartz管理工具类
 *
 * @author Caratacus
 */
@Slf4j
@Component
public class QuartzManage {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(Job quartzJob) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzExecutionJob.class).
                    withIdentity(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron()))
                    .build();

            cronTrigger.getJobDataMap().put(QuartzCons.JOB_KEY_PREFIX, quartzJob);

            //重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (quartzJob.getPaused()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "创建定时任务失败", e);
        }
    }

    /**
     * 更新job cron表达式
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void updateJobCron(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(QuartzCons.JOB_KEY_PREFIX, quartzJob);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (quartzJob.getPaused()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "更新定时任务失败", e);

        }

    }

    /**
     * 删除一个job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void deleteJob(Job quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "删除定时任务失败", e);

        }
    }

    /**
     * 恢复一个job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void resumeJob(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null)
                addJob(quartzJob);
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "恢复定时任务失败", e);
        }
    }

    /**
     * 立即执行job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void runAJobNow(Job quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(QuartzCons.JOB_KEY_PREFIX, quartzJob);
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "定时任务执行失败", e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param quartzJob
     * @throws SchedulerException
     */
    public void pauseJob(Job quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(QuartzCons.JOB_NAME_PREFIX + quartzJob.getJobId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "定时任务暂停失败", e);
        }
    }
}
