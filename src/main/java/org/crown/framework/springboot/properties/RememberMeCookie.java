package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RememberMeCookie {

    /**
     * 设置Cookie的域名 默认空，即当前访问的域名
     */
    private String domain;
    /**
     * 设置cookie的有效访问路径
     */
    private String path;
    /**
     * 设置HttpOnly属性
     */
    private boolean httpOnly;
    /**
     * 设置Cookie的过期时间，天为单位
     */
    private int maxAge;
}
