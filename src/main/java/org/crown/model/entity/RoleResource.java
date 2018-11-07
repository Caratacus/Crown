package org.crown.model.entity;

import org.crown.common.framework.model.BaseModel;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 角色资源关系表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_resource")
public class RoleResource extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer resourceId;


    public static final String ROLE_ID = "role_id";

    public static final String RESOURCE_ID = "resource_id";

}
