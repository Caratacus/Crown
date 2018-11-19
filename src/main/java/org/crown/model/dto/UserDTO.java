package org.crown.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.crown.common.framework.model.convert.Convert;
import org.crown.emuns.UserStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户DTO
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "主键")
    private Integer id;
    @ApiModelProperty(notes = "登陆名")
    private String loginName;
    @ApiModelProperty(notes = "昵称")
    private String nickname;
    @ApiModelProperty(notes = "邮箱")
    private String email;
    @ApiModelProperty(notes = "手机")
    private String phone;
    @ApiModelProperty(notes = "IP地址")
    private String ip;
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private UserStatusEnum status;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(notes = "用户角色ID")
    private List<Integer> roleIds;
}
