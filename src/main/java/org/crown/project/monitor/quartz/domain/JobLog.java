package org.crown.project.monitor.quartz.domain;

import java.util.Date;

import org.crown.common.annotation.Excel;
import org.crown.framework.web.domain.BaseQueryParams;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author Caratacus
 */
@Setter
@Getter
public class JobLog extends BaseQueryParams {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "日志序号")
    @TableId
    private Long jobLogId;
    /**
     * 执行类名
     */
    @Excel(name = "执行类名")
    @ApiModelProperty(notes = "执行类名")
    private String className;
    @ApiModelProperty(notes = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * Cron表达式
     */
    @Excel(name = "Cron表达式")
    @ApiModelProperty(notes = "Cron表达式")
    private String cron;
    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    @ApiModelProperty(notes = "异常信息")
    private String exception;
    /**
     * 执行状态（1成功 0失败）
     */
    @Excel(name = "执行状态", readConverterExp = "1=成功,0=失败")
    @ApiModelProperty(notes = "执行状态")
    private Integer status;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @ApiModelProperty(notes = "任务名称")
    private String jobName;
    /**
     * 参数
     */
    @Excel(name = "参数")
    @ApiModelProperty(notes = "参数")
    private JSONObject jobParams;
    /**
     * 运行时长
     */
    @Excel(name = "运行时长")
    @ApiModelProperty(notes = "运行时长")
    private String runTime;

}
