package org.crown.common.framework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 自增主键父类
 * </p>
 *
 * @author Caratacus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseModel extends BaseConvert{

    public static final String ID = "id";

    private Integer id;

}
