package org.crown.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import org.crown.common.framework.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单资源关系表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu_resource")
public class MenuResource extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 资源ID
     */
    private Integer resourceId;


    public static final String MENU_ID = "menu_id";

    public static final String RESOURCE_ID = "resource_id";

}
