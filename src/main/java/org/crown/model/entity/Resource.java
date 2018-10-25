package org.crown.model.entity;

import org.crown.common.framework.model.BaseModel;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_resource")
public class Resource extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 路径映射
     */
    private String mapping;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 是否需要验证
     */
    private Boolean verify;


    public static final String RESOURCE_NAME = "resource_name";

    public static final String MAPPING = "mapping";

    public static final String METHOD = "method";

    public static final String VERIFY = "verify";

}
