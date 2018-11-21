package org.crown.emuns;

import org.crown.common.emuns.IEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>
 * 用户状态枚举
 * </p>
 *
 * @author Caratacus
 */
public enum UserStatusEnum implements IEnum {

    NORMAL(0), DISABLE(1);

    @EnumValue
    private final int value;

    UserStatusEnum(final int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }
}
