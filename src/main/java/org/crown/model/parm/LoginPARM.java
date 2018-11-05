package org.crown.model.parm;

import javax.validation.constraints.NotBlank;

import org.crown.common.framework.model.convert.Convert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 登陆参数
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginPARM extends Convert {

    @ApiModelProperty(notes = "账号")
    @NotBlank(message = "账号有误！")
    private String loginName;
    @ApiModelProperty(notes = "密码")
    @NotBlank(message = "账号有误！")
    private String password;

}
