package org.crown.project.monitor.quartz.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.crown.common.annotation.Excel;
import org.crown.common.utils.StringUtils;
import org.crown.framework.spring.validator.Cron;
import org.crown.framework.web.domain.BaseEntity;
import org.crown.project.monitor.quartz.common.CronUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author Caratacus
 */
@Setter
@Getter
public class Job extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @Excel(name = "任务序号")
    @TableId
    private Long jobId;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @ApiModelProperty(notes = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    @Size(max = 64, message = "任务名称不能超过64个字符")
    private String jobName;
    /**
     * 执行类名
     */
    @Excel(name = "执行类名")
    @ApiModelProperty(notes = "执行类名")
    @NotBlank(message = "执行类名不能为空")
    @Size(max = 255, message = "执行类名不能超过255个字符")
    private String className;
    /**
     * 参数
     */
    @Excel(name = "参数")
    @ApiModelProperty(notes = "参数")
    private JSONObject jobParams;
    /**
     * Cron表达式
     */
    @Excel(name = "Cron表达式")
    @ApiModelProperty(notes = "Cron表达式")
    @NotBlank(message = "Cron执行表达式不能为空")
    @Cron(message = "Cron执行表达式不正确")
    private String cron;
    /**
     * 任务状态
     */
    @Excel(name = "任务状态", readConverterExp = "true=暂停,false=运行中")
    @ApiModelProperty(notes = "任务状态")
    private Boolean paused;
    /**
     * 备注
     */
    @Excel(name = "备注")
    @ApiModelProperty(notes = "备注")
    private String remark;
    /**
     * 删除标志（0代表存在1代表删除）
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 获取下次执行时间
     *
     * @return
     */
    public Date getNextValidTime() {
        String cron = getCron();
        if (StringUtils.isNotEmpty(cron)) {
            return CronUtils.getNextExecution(cron);
        }
        return null;
    }
}
