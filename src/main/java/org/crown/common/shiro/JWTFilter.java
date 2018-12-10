package org.crown.common.shiro;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.crown.common.utils.JWTUtils;
import org.crown.cons.APICons;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ResponseUtils;
import org.crown.model.dto.ResourcePermDTO;
import org.crown.service.IResourceService;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import lombok.extern.slf4j.Slf4j;


/**
 * JWT过滤器 适用于shiro
 *
 * @author Caratacus
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private PathMatcher pathMatcher;
    private IResourceService resourceService;
    private UrlPathHelper urlPathHelper;

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
        request.setAttribute(APICons.API_BEGIN_TIME, System.currentTimeMillis());
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);

        String token = getToken(httpRequest);
        String method = httpRequest.getMethod();
        String requestUri = urlPathHelper.getOriginatingRequestUri(httpRequest);
        Optional<ResourcePermDTO> optional = resourceService.getResourcePerms(method).stream().filter(match(method, requestUri)).findFirst();
        request.setAttribute(APICons.API_REQURL, requestUri);
        request.setAttribute(APICons.API_METHOD, method);
        if (optional.isPresent()) {
            request.setAttribute(APICons.API_MAPPING, optional.get().getMapping());
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        if (Objects.isNull(token)) {
            List<ResourcePermDTO> openPerms = resourceService.getOpenPerms();
            return anyMatch(openPerms, method, requestUri);
        }
        if (isLoginRequest(request, response)) {
            if (executeLogin(request, response)) {
                Integer uid = JWTUtils.getUid(token);
                request.setAttribute(APICons.API_UID, uid);
                Set<ResourcePermDTO> perms = resourceService.getUserResourcePerms(uid);
                return anyMatch(perms, method, requestUri);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return sendUnauthorizedFail(request, response);
            }
        }
        return false;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        switch (httpResponse.getStatus()) {
            case HttpServletResponse.SC_NOT_FOUND:
                return sendNotFoundFail(request, response);
            case HttpServletResponse.SC_UNAUTHORIZED:
                return sendUnauthorizedFail(request, response);
            default:
                return sendForbiddenFail(request, response);
        }
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
        String token = request.getHeader(AUTHORIZATION_HEADER);
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
     * 路径不存在
     */
    protected boolean sendNotFoundFail(ServletRequest request, ServletResponse response) {
        ResponseUtils.sendFail(WebUtils.toHttp(request), WebUtils.toHttp(response), ErrorCodeEnum.NOT_FOUND);
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
    protected boolean anyMatch(Collection<ResourcePermDTO> perms, String method, String requestUri) {
        return perms.stream().anyMatch(match(method, requestUri));
    }

    /**
     * 匹配请求方法与路径
     *
     * @param method
     * @param requestUri
     * @return
     */
    private Predicate<ResourcePermDTO> match(String method, String requestUri) {
        return res -> res.getMethod().equalsIgnoreCase(method) && pathMatcher.match(res.getMapping(), requestUri);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        try {
            return super.executeLogin(request, response);
        } catch (Exception ignored) {
        }
        return false;
    }

    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

}