package org.crown.framework.springboot.properties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Xss {

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除字段
     */
    private List<String> excludeFields;
    /**
     * 排除路径
     */
    private List<String> excludeUrls;

}