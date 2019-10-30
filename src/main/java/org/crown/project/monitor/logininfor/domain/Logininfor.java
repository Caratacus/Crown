package org.crown.project.monitor.logininfor.domain;

import java.util.Date;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseQueryParams;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author Crown
 */
@Setter
@Getter
public class Logininfor extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号")
    @TableId
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String loginName;

    /**
     * 登录状态 0成功 1失败
     */
    @Excel(name = "登录状态", readConverterExp = "1=成功,0=失败")
    private Integer status;

    /**
     * 登录IP地址
     */
    @Excel(name = "登录地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Excel(name = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统 ")
    private String os;

    /**
     * 提示消息
     */
    @Excel(name = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}