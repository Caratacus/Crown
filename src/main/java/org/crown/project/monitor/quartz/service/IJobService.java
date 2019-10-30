package org.crown.project.monitor.quartz.service;

import java.util.List;

import org.crown.framework.service.BaseService;
import org.crown.project.monitor.quartz.domain.Job;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author Caratacus
 */
public interface IJobService extends BaseService<Job> {

    /**
     * create
     *
     * @param resources
     * @return
     */
    Job create(Job resources);

    /**
     * update
     *
     * @param resources
     * @return
     */
    void update(Job resources);

    /**
     * del
     *
     * @param quartzJob
     */
    void delete(Job quartzJob);

    /**
     * 更改定时任务状态
     *
     * @param quartzJob
     */
    void updatePaused(Job quartzJob);

    /**
     * 立即执行定时任务
     *
     * @param quartzJob
     */
    void execute(Job quartzJob);

    /**
     * 查询定时任务列表
     *
     * @param job
     * @return
     */
    List<Job> selectJobList(Job job);
}
