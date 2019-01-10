package org.crown.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crown.common.shiro.JWTFilter;
import org.crown.common.shiro.JWTRealm;
import org.crown.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * Shiro 配置
 *
 * @author Caratacus
 */
@Configuration
public class ShiroAutoConfiguration {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public SecurityManager securityManager(@Autowired JWTRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        /*
         * 关闭shiro自带的session
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        evaluator.setSessionStorageEnabled(false);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager, @Autowired IResourceService resourceService) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        JWTFilter filter = new JWTFilter();
        filter.setAuthzScheme("Bearer");
        filter.setUrlPathHelper(new UrlPathHelper());
        filter.setResourceService(resourceService);
        filter.setPathMatcher(new AntPathMatcher());
        filter.setContextPath(cleanContextPath(contextPath));
        filters.put("jwt", filter);
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**/**.*", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    private String cleanContextPath(String contextPath) {
        if (StringUtils.hasText(contextPath) && contextPath.endsWith("/")) {
            return contextPath.substring(0, contextPath.length() - 1);
        }
        return contextPath;
    }
}
