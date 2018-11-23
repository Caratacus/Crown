/*
 * Copyright (c) 2018-2019 Caratacus, (caratacus@qq.com).
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
package org.crown.common.http.log;

import java.util.Map;
import java.util.Optional;

import org.crown.common.http.ResponseKit;
import org.crown.common.kit.JacksonUtils;
import org.crown.common.spring.ApplicationUtils;

/**
 * 请求日志工具类
 *
 * @author Caratacus
 */
public abstract class LogHelper {

    /**
     * 获取日志对象
     *
     * @param beiginTime
     * @param parameterMap
     * @param requestBody
     * @param url
     * @param mapping
     * @param method
     * @param ip
     * @param object
     * @return
     */
    public static Log getLogger(Long beiginTime, Map<String, String[]> parameterMap, String requestBody, String url, String mapping, String method, String ip, Object object) {
        // 调用接口结束时间
        Long endTime = System.currentTimeMillis();
        return Log.builder()
                //查询参数
                .parameterMap(parameterMap)
                //请求体
                .requestBody(Optional.ofNullable(JacksonUtils.parse(requestBody)).orElse(requestBody))
                //请求路径
                .url(url)
                //请求mapping
                .mapping(mapping)
                //请求方法
                .method(method)
                .runTime((beiginTime != null ? endTime - beiginTime : 0) + "ms")
                .result(object)
                .ip(ip)
                .build();
    }

    public static void doAfterReturning(Object ret) {
        ResponseKit.writeValAsJson(ApplicationUtils.getRequest(), ret);
    }
}
