package org.crown.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import org.crown.common.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户角色关系表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_role")
public class UserRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 角色ID
     */
    private Integer roleId;


    public static final String UID = "uid";

    public static final String ROLE_ID = "role_id";

}
