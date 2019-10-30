package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Shiro相关配置
 *
 * @author Caratacus
 */
@ConfigurationProperties(prefix = ShiroProperties.SHIRO_PREFIX)
@Setter
@Getter
public class ShiroProperties {

    public static final String SHIRO_PREFIX = "shiro";

    /**
     * 登录地址
     */
    private String loginUrl;
    /**
     * 权限认证失败地址
     */
    private String unauthUrl;
    /**
     * 首页地址
     */
    private String indexUrl;
    /**
     * 记住我
     */
    @NestedConfigurationProperty
    private RememberMeCookie rememberMeCookie;
    /**
     * Session配置
     */
    @NestedConfigurationProperty
    private Session session;
}
