package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Crown项目相关配置
 *
 * @author Caratacus
 */
@ConfigurationProperties(prefix = CrownProperties.CROWN_PREFIX)
@Setter
@Getter
public class CrownProperties {

    public static final String CROWN_PREFIX = "crown";

    /**
     * 实例演示开关
     */
    @NestedConfigurationProperty
    private Demo demo;

    /**
     * 路径
     */
    @NestedConfigurationProperty
    private Path path;

    /**
     * 获取地址开关
     */
    @NestedConfigurationProperty
    private Address address;
    /**
     * 用户密码配置
     */
    @NestedConfigurationProperty
    private Password password;
    /**
     * 生成代码配置
     */
    @NestedConfigurationProperty
    private Generator generator;
    /**
     * Xss配置
     */
    @NestedConfigurationProperty
    private Xss xss;
    /**
     * 告警通知Email配置
     */
    @NestedConfigurationProperty
    private Email email;

}
