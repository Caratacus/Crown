package org.crown.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.crown.common.cons.APICons;
import org.crown.framework.servlet.wrapper.GlobalRequestWrapper;
import org.springframework.web.util.UrlPathHelper;

/**
 * 防止XSS攻击的过滤器
 *
 * @author Crown
 */
public class GlobalFilter implements Filter {

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        request.setAttribute(APICons.API_BEGIN_TIME, System.currentTimeMillis());
        String requestUri = urlPathHelper.getOriginatingRequestUri(req);
        request.setAttribute(APICons.API_REQURL, requestUri);
        GlobalRequestWrapper xssRequest = new GlobalRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

}