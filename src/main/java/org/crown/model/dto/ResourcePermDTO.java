package org.crown.model.dto;

import org.crown.common.framework.model.convert.Convert;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 权限 资源DTO
 * </p>
 *
 * @author Caratacus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourcePermDTO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "请求方式")
    private String method;

    @ApiModelProperty(notes = "路径映射")
    private String mapping;


}
