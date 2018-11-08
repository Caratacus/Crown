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
public class PasswordPARM extends Convert {

    @ApiModelProperty(notes = "原密码")
    @NotBlank(message = "原密码不能为空", groups = Update.class)
    private String oldPassword;
    @ApiModelProperty(notes = "新密码")
    @NotBlank(message = "新密码不能为空", groups = Update.class)
    private String newPassword;

    public interface Update {

    }
}
