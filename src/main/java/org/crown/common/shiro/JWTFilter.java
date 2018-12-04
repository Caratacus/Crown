package org.crown.common.shiro;

import java.util.List;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.crown.common.spring.ApplicationUtils;
import org.crown.common.utils.JWTTokenUtils;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ResponseUtils;
import org.crown.model.dto.ResourcePermDTO;
import org.crown.service.IResourceService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import lombok.extern.slf4j.Slf4j;


/**
 * JWT过滤器 适用于shiro
 *
 * @author Caratacus
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        //获取请求token
        String token = getToken(WebUtils.toHttp(servletRequest));
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return StringUtils.isBlank(token) ? null : new JWTToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        IResourceService resourceService = ApplicationUtils.getBean(IResourceService.class);
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        log.info(httpRequest.getRequestURL().toString());
        String token = getToken(httpRequest);
        String method = httpRequest.getMethod();
        String requestUri = urlPathHelper.getOriginatingRequestUri(httpRequest);
        if (Objects.isNull(token)) {
            List<ResourcePermDTO> openPerms = resourceService.getOpenPerms();
            return anyMatch(openPerms, method, requestUri);
        }

        if (isLoginRequest(request, response)) {
            if (executeLogin(request, response)) {
                Integer uid = JWTTokenUtils.getUid(token);
                List<ResourcePermDTO> perms = resourceService.getUserResourcePerms(uid);
                return anyMatch(perms, method, requestUri);
            } else {
                return sendUnauthorizedFail(request, response);
            }
        }
        return false;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        return sendForbiddenFail(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        return sendUnauthorizedFail(request, response);
    }

    /**
     * 获取请求的token
     */
    protected String getToken(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader("Authorization");
        return StringUtils.isBlank(token) ? null : token.replaceFirst("Bearer ", "");
    }

    /**
     * 无权限
     */
    protected boolean sendForbiddenFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.FORBIDDEN);
        return false;
    }

    /**
     * 未认证
     */
    protected boolean sendUnauthorizedFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.UNAUTHORIZED);
        return false;
    }


    /**
     * 是否任意匹配权限URL
     *
     * @param perms
     * @return
     */
    protected boolean anyMatch(List<ResourcePermDTO> perms, String method, String requestUri) {
        return perms.stream().anyMatch(res -> res.getMethod().equalsIgnoreCase(method) && antPathMatcher.match(res.getMapping(), requestUri));
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        try {
            return super.executeLogin(request, response);
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public String getAuthzScheme() {
        return "Bearer";
    }

}