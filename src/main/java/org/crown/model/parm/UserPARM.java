package org.crown.model.parm;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.crown.common.framework.model.convert.Convert;
import org.crown.cons.Regex;
import org.crown.emuns.UserStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户PARM
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPARM extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "登陆名")
    @NotBlank(groups = {Create.class, Update.class}, message = "用户名不能为空")
    private String loginName;
    @ApiModelProperty(notes = "昵称")
    @NotBlank(groups = {Create.class, Update.class}, message = "昵称不能为空")
    private String nickname;
    @Email(groups = {Create.class, Update.class}, message = "邮箱格式不正确")
    @ApiModelProperty(notes = "邮箱")
    private String email;
    @Pattern(groups = {Create.class, Update.class}, regexp = Regex.PHONE, message = "手机号码格式不正确")
    @ApiModelProperty(notes = "手机号")
    private String phone;
    @NotNull(groups = Status.class, message = "用户状态不能为空")
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private UserStatusEnum status;
    @ApiModelProperty(notes = "用户角色ID")
    @NotEmpty(groups = {Create.class, Update.class}, message = "用户角色不能为空")
    private List<Integer> roleIds;

    public interface Create {

    }

    public interface Update {

    }

    public interface Status {

    }

}
