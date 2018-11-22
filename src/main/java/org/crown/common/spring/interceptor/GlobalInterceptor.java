package org.crown.common.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.annotations.Resources;
import org.crown.common.exception.CrownException;
import org.crown.cons.APICons;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;


/**
 * 全局拦截器
 *
 * @author Caratacus
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ResourceHttpRequestHandler放行
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        request.setAttribute(APICons.API_BEGIN_TIME, System.currentTimeMillis());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取请求路径
            setAttributeOfPath(request, handlerMethod);
            Resources resources = handlerMethod.getMethodAnnotation(Resources.class);
            //ApiAssert.isTrue(ErrorCodeEnum.DEMO_SYSTEM_CANNOT_DO, Objects.nonNull(resources) && !resources.verify());

        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 获取当前请求路径放入request中
     *
     * @param request
     */
    public void setAttributeOfPath(HttpServletRequest request, HandlerMethod handlerMethod) {
        RequestMapping requestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        RequestMapping methodAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
        String mapping = "";
        if (requestMapping != null) {
            mapping = requestMapping.value()[0];
        }
        String methodMapping = "";
        if (methodAnnotation == null) {
            throw new CrownException("请求路径解析错误");
        }
        String[] methodMappings = methodAnnotation.value();
        if (ArrayUtils.isNotEmpty(methodMappings)) {
            methodMapping = methodMappings[0];
        }
        String requestMethod = methodAnnotation.method()[0].name();
        String requsetMapping = mapping + methodMapping;
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String reqUrl = requestUri.substring(contextPath.length());
        request.setAttribute(APICons.API_REQURL, reqUrl);
        request.setAttribute(APICons.API_MAPPING, requsetMapping);
        request.setAttribute(APICons.API_METHOD, requestMethod);
    }

}