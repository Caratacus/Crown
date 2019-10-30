/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.framework.utils;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.crown.common.utils.JsonUtils;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.framework.model.Log;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.project.monitor.exceLog.domain.ExceLog;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求日志工具类
 *
 * @author Caratacus
 */
@Slf4j
public abstract class LogUtils {

    /**
     * 获取日志对象
     *
     * @param status
     * @param beiginTime
     * @param parameterMap
     * @param requestBody
     * @param url
     * @param actionMethod
     * @param method
     * @param ip
     * @param object
     * @return
     */
    public static void printLog(Integer status, Long beiginTime, String uid, String loginName, Map<String, String[]> parameterMap, Object requestBody, String url, String actionMethod, String method, String ip, Object object) {
        String runTime = (beiginTime != null ? System.currentTimeMillis() - beiginTime : 0) + "ms";
        Log logInfo = Log.builder()
                //查询参数
                .parameterMap(parameterMap)
                .uid(uid)
                .loginName(loginName)
                //请求体r
                .requestBody(requestBody)
                //请求路径
                .url(url)
                //控制器方法
                .actionMethod(actionMethod)
                //请求方法
                .method(method)
                .runTime(runTime)
                .result(object)
                .ip(ip)
                .build();
        String logJson = JsonUtils.toJson(logInfo);
        if (status >= HttpServletResponse.SC_BAD_REQUEST) {
            ExceLog exceLog = new ExceLog();
            exceLog.setOperName(loginName);
            exceLog.setUrl(url);
            exceLog.setActionMethod(actionMethod);
            exceLog.setRunTime(runTime);
            exceLog.setContent(logJson);
            ThreadExecutors.execute(TimerTasks.saveExceLog(ip, status, exceLog));
        }

        log.info(logJson);
    }

    public static void doAfterReturning(Object ret) {
        ResponseUtils.writeValAsJson(ApplicationUtils.getRequest(), ret);
    }

}
