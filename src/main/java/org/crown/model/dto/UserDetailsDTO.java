package org.crown.model.dto;

import java.util.List;

import org.crown.common.framework.model.convert.Convert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户详情DTO
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDetailsDTO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "登陆名")
    private String loginName;

    @ApiModelProperty(notes = "昵称")
    private String nikeName;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "手机")
    private String phone;

    @ApiModelProperty(notes = "权限路径")
    private List<ResourcePermDTO> perms;

}
