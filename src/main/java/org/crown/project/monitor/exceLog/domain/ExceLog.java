package org.crown.project.monitor.exceLog.domain;

import java.util.Date;

import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常日志表 sys_exce_log
 *
 * @author Caratacus
 */
@Setter
@Getter
public class ExceLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 当前操作人
     */
    private String operName;
    /**
     * 请求路径
     */
    private String url;
    /**
     * 控制器方法
     */
    private String actionMethod;
    /**
     * 接口运行时间 单位:ms
     */
    private String runTime;
    /**
     * IP地址
     */
    private String ipAddr;
    /**
     * 日志详情
     */
    private String content;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

}
