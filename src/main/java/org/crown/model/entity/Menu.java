package org.crown.model.entity;

import java.time.LocalDateTime;

import org.crown.common.framework.model.BaseModel;
import org.crown.emuns.MenuTypeEnum;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Menu extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 路径
     */
    private String path;

    /**
     * 类型:1:目录,2:菜单,3:按钮
     */
    private MenuTypeEnum menuType;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建者ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createUid;

    /**
     * 修改者ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUid;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static final String PARENT_ID = "parent_id";

    public static final String MENU_NAME = "menu_name";

    public static final String PATH = "path";

    public static final String MENU_TYPE = "menu_type";

    public static final String ICON = "icon";

    public static final String CREATE_UID = "create_uid";

    public static final String UPDATE_UID = "update_uid";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
