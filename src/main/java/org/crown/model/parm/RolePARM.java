package org.crown.model.parm;

import javax.validation.constraints.NotBlank;

import org.crown.common.framework.model.convert.Convert;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RolePARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "角色名称")
    @NotBlank(groups = {Create.class, Update.class}, message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(notes = "备注")
    private String remark;

    public interface Create {

    }

    public interface Update {

    }
}
