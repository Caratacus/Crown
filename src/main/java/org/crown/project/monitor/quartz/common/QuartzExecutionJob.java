package org.crown.project.monitor.quartz.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.crown.common.cons.Constants;
import org.crown.common.cons.QuartzCons;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.project.monitor.quartz.domain.Job;
import org.crown.project.monitor.quartz.domain.JobLog;
import org.crown.project.monitor.quartz.service.IJobLogService;
import org.crown.project.monitor.quartz.service.IJobService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

/**
 * Quartz执行定时任务
 *
 * @author Caratacus
 */
@Async
@Slf4j
public class QuartzExecutionJob extends QuartzJobBean {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job quartzJob = (Job) context.getMergedJobDataMap().get(QuartzCons.JOB_KEY_PREFIX);
        /* 获取spring bean */
        IJobService jobService = ApplicationUtils.getBean(IJobService.class);
        IJobLogService jobLogService = ApplicationUtils.getBean(IJobLogService.class);
        QuartzManage quartzManage = ApplicationUtils.getBean(QuartzManage.class);

        JSONObject jobParams = quartzJob.getJobParams();
        JobLog jobLog = new JobLog();
        jobLog.setJobName(quartzJob.getJobName());
        jobLog.setClassName(quartzJob.getClassName());
        jobLog.setJobParams(jobParams);
        long startTime = System.currentTimeMillis();
        jobLog.setCron(quartzJob.getCron());
        try {
            // 执行任务
            log.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getClassName(), quartzJob.getJobId(),
                    jobParams);
            Future<?> future = executorService.submit(task);
            future.get();
            String runTime = System.currentTimeMillis() - startTime + "ms";
            jobLog.setRunTime(runTime);
            // 任务状态
            jobLog.setStatus(Constants.SUCCESS);
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), runTime);
        } catch (Exception e) {
            log.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            jobLog.setRunTime(System.currentTimeMillis() - startTime + "ms");
            // 任务状态 0：成功 1：失败
            jobLog.setStatus(Constants.FAIL);
            jobLog.setException(Throwables.getStackTraceAsString(e));
            //出错就暂停任务
            quartzManage.pauseJob(quartzJob);
            //更新状态
            jobService.updatePaused(quartzJob);
        } finally {
            jobLogService.save(jobLog);
        }
    }
}
