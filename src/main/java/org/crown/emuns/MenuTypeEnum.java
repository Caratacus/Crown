package org.crown.emuns;

import org.crown.common.emuns.IEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>
 * 菜单类型枚举
 * </p>
 *
 * @author Caratacus
 */
public enum MenuTypeEnum implements IEnum {

    /**
     * 目录
     */
    CATALOG(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    @EnumValue
    private final int value;

    MenuTypeEnum(final int value) {
        this.value = value;
    }

    @JsonValue
    public int value() {
        return this.value;
    }

    @Override
    public int getValue() {
        return value();
    }
}
