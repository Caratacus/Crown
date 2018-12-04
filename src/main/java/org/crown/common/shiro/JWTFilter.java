package org.crown.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ResponseUtils;


/**
 * JWT过滤器 适用于shiro
 *
 * @author Caratacus
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取请求token
        String token = getToken((HttpServletRequest) servletRequest);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return StringUtils.isBlank(token) ? null : new JWTToken(token);
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println(((HttpServletRequest) request).getRequestURL());
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                ResponseUtils.sendFail((HttpServletRequest) request, (HttpServletResponse) response, ErrorCodeEnum.UNAUTHORIZED);
            }
        }
        return true;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        //获取请求token，如果token不存在，直接返回401
        String token = getToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            ResponseUtils.sendFail((HttpServletRequest) request, (HttpServletResponse) response, ErrorCodeEnum.UNAUTHORIZED);
            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail((HttpServletRequest) request, (HttpServletResponse) response, ErrorCodeEnum.UNAUTHORIZED);
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getToken(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader("Authorization");
        return StringUtils.isBlank(token) ? null : token.replaceFirst("Bearer ", "");
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    @Override
    public void setAuthzScheme(String authcScheme) {
        super.setAuthcScheme("Bearer");
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        JWTToken token = new JWTToken(getToken((HttpServletRequest) request));
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }


}
