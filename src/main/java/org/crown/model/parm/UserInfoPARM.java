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
 * 用户信息 PARM
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserInfoPARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "昵称")
    @NotBlank(groups = Update.class, message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(notes = "邮箱")
    private String email;

    @ApiModelProperty(notes = "手机")
    private String phone;

    public interface Update {

    }

}
