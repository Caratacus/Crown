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
    CATALOG(1),
    /**
     * 菜单
     */
    MENU(2),
    /**
     * 按钮
     */
    BUTTON(3);

    @EnumValue
    private final int value;

    MenuTypeEnum(final int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }
}
