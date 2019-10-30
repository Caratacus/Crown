package org.crown.framework.springboot.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Path {

    /**
     * 上传路径
     */
    private String filePath;
    /**
     * 资源Handler
     */
    private String resourceHandler;
    /**
     * 资源路径
     */
    private String resourcePath;
    /**
     * 前缀对象
     */
    @NestedConfigurationProperty
    private Prefix prefix;
}