package org.crown.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.crown.common.http.RequestKit;
import org.crown.common.http.wrapper.ApiRequestWrapper;
import org.springframework.stereotype.Component;

/**
 * Crown 过滤器
 *
 * @author Caratacus
 */
@Component
@WebFilter(filterName = "crownFilter", urlPatterns = "/*")
public class CrownFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        //包含请求才设置RequestWrapper
        if (RequestKit.isContainBody(req)) {
            req = new ApiRequestWrapper(req);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig config) {
    }

}