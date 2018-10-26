package org.crown.common.http.log;

import java.util.Map;
import java.util.Optional;

import org.crown.common.kit.FastJsonUtils;
import org.crown.common.http.ResponseKit;
import org.crown.common.spring.ApplicationUtils;


/**
 * 请求日志工具类
 *
 * @author Caratacus
 * @since 2018-7-13
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
                .requestBody(Optional.ofNullable(FastJsonUtils.parse(requestBody)).orElse(requestBody))
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
