package org.crown.project.monitor.quartz.common;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 定时任务执行接口
 * </p>
 *
 * @author Caratacus
 */
public interface IExecuteQuartzJob {

    /**
     * 立即执行
     *
     * @param jobId
     * @param params
     */
    void execute(Long jobId, JSONObject params);

}
