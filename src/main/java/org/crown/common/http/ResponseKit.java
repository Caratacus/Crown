package org.crown.common.http;

import javax.servlet.http.HttpServletRequest;

import org.crown.common.http.log.Log;
import org.crown.common.http.log.LogHelper;
import org.crown.common.http.wrapper.ApiResponseWrapper;
import org.crown.common.kit.IpUtils;
import org.crown.common.kit.JacksonUtils;
import org.crown.cons.APICons;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * response输出工具类
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class ResponseKit {

    /**
     * Portal输出json字符串
     *
     * @param response
     * @param obj      需要转换JSON的对象
     */
    public static void writeValAsJson(HttpServletRequest request, ApiResponseWrapper response, Object obj) {
        Log logger = LogHelper.getLogger((Long) request.getAttribute(APICons.API_BEGIN_TIME),
                request.getParameterMap(),
                RequestKit.getRequestBody(request),
                (String) request.getAttribute(APICons.API_REQURL),
                (String) request.getAttribute(APICons.API_MAPPING),
                (String) request.getAttribute(APICons.API_METHOD),
                IpUtils.getIpAddr(request),
                obj);
        // 日志打印
        log.info(JacksonUtils.toJson(logger));
        if (ObjectUtils.isNotNull(response, obj)) {
            response.writeValueAsJson(obj);
        }
    }

    /**
     * 打印日志信息但是不输出到浏览器
     *
     * @param request
     * @param obj
     */
    public static void writeValAsJson(HttpServletRequest request, Object obj) {
        writeValAsJson(request, null, obj);
    }

}
