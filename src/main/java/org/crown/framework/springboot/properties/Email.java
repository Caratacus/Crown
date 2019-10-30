package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Email {

    /**
     * 开关
     */
    private boolean enabled = false;
    /**
     * 告警邮箱地址
     */
    private String send;
}