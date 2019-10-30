package org.crown.framework.consolelog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 页面控制台日志实体
 *
 * @author Caratacus
 * @link https://cloud.tencent.com/developer/article/1096792
 */
@Setter
@Getter
@AllArgsConstructor
public class ConsoleLog {

    public static final String VIEW_PERM = "monitor:consolelog:view";

    /**
     * 日志内容
     */
    private String body;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 类名
     */
    private String fileName;
    /**
     * 行号
     */
    private int lineNumber;
    /**
     * 线程名
     */
    private String threadName;
    /**
     * 日志等级
     */
    private String level;
}
