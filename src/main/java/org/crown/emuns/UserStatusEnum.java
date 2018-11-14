package org.crown.emuns;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>
 * 用户状态枚举
 * </p>
 *
 * @author Caratacus
 */
public enum UserStatusEnum {

    NOMAL(0), DISABLE(1);

    @EnumValue
    private final int code;

    UserStatusEnum(final int code) {
        this.code = code;
    }

    @JsonValue
    public int code() {
        return this.code;
    }

}
