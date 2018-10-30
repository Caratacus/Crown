package org.crown.model.entity;

import java.time.LocalDateTime;

import org.crown.common.framework.model.BaseModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String nikeName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 状态 0：禁用 1：正常
     */
    private Integer status;

    /**
     * 创建者ID
     */
    private Integer createUid;

    /**
     * 修改者ID
     */
    private Integer updateUid;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    public static final String UID = "uid";

    public static final String NIKE_NAME = "nike_name";

    public static final String EMAIL = "email";

    public static final String PHONE = "phone";

    public static final String STATUS = "status";

    public static final String CREATE_UID = "create_uid";

    public static final String UPDATE_UID = "update_uid";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
