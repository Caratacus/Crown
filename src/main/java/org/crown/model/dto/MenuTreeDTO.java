package org.crown.model.dto;

import org.crown.common.framework.model.TreeNode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 菜单树DTO
 * </p>
 *
 * @author Mybatis Plus
 * @since 2018-7-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MenuTreeDTO extends TreeNode {

    @ApiModelProperty(notes = "菜单名称", dataType = "string")
    private String name;
    @ApiModelProperty(notes = "类型:1:目录,2:菜单,3:按钮", dataType = "int")
    private Integer type;
    @ApiModelProperty(notes = "映射地址", dataType = "string")
    private String mapping;
    @ApiModelProperty(notes = "图标", dataType = "string")
    private String icon;

}
