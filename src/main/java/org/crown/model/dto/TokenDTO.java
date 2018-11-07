package org.crown.model.dto;

import org.crown.common.framework.model.convert.Convert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * TokenDTO
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TokenDTO extends Convert {

    @ApiModelProperty(notes = "账号id")
    private Integer uid;
    @ApiModelProperty(notes = "token")
    private String token;

}
