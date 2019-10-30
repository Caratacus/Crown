package org.crown.common.interceptors;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.APICons;
import org.crown.framework.spring.ApplicationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 通用拦截器
 *
 * @author Caratacus
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RestController restController = handlerMethod.getBeanType().getAnnotation(RestController.class);
            boolean isRest = Objects.nonNull(restController);
            ApplicationUtils.getRequest().setAttribute(APICons.API_REST, isRest);
            ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
            boolean isRestResult = Objects.nonNull(responseBody);
            ApplicationUtils.getRequest().setAttribute(APICons.API_REST_RESULT, isRest || isRestResult);
        }
        return super.preHandle(request, response, handler);
    }

}